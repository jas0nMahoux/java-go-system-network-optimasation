package com.nathansakkriou;

import com.nathansakkriou.entities.concept.Concept;
import com.nathansakkriou.entities.network.Edge;
import com.nathansakkriou.entities.network.NetworkConcepts;
import com.nathansakkriou.repository.IConceptRepository;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Optimisation de la méthode de recherche de delimiter [[]]
 */
public class GetNetworkUseCaseIterative4 {
    private final IConceptRepository conceptRepository;

    public GetNetworkUseCaseIterative4(IConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    public NetworkConcepts execute(boolean showDeleted) {
        List<Edge> edges = new ArrayList<>();
        List<Concept> allConcept = showDeleted ? conceptRepository.findAll() : conceptRepository.findAllExcludeDeleted();
        Map<String, UUID> mapTitleId = conceptsToMapTitleUuid(allConcept);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (Concept concept : allConcept)
                executor.execute(() -> {
                    edges.addAll(findEdges(concept, mapTitleId));
                });
        }

        return new NetworkConcepts(allConcept, edges);
    }

    private Set<Edge> findEdges(Concept concept, Map<String, UUID> mapTitleId) {
        Set<String> refs = extractTextBetweenDelimiters(concept.getDescription());
        Set<Edge> edges = new HashSet<>();
        refs.forEach(ref -> {
            if (mapTitleId.containsKey(ref)) {
                UUID conceptId = mapTitleId.get(ref);
                edges.add(new Edge(concept.getId(), conceptId));
            }
        });
        return edges;
    }

    private static Set<String> extractTextBetweenDelimiters(String input) {
        Set<String> results = new HashSet<>();
        int index = 0;
        while ((index = input.indexOf("[[", index)) != -1) {
            int end = input.indexOf("]]", index);
            if (end == -1) break;
            results.add(input.substring(index + 2, end));
            index = end + 2;
        }
        return results;
    }

    private Map<String, UUID> conceptsToMapTitleUuid(List<Concept> concepts) {
        return concepts.stream()
                .parallel()
                .collect(
                        Collectors.toMap(Concept::getTitle, Concept::getId)
                );
    }
}
