package com.nathansakkriou;

import com.nathansakkriou.entities.concept.Concept;
import com.nathansakkriou.entities.network.Edge;
import com.nathansakkriou.entities.network.NetworkConcepts;
import com.nathansakkriou.repository.IConceptRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Optimisation de la méthode de recherche de delimiter [[]] extraction du pattern en const globale
 */
public class GetNetworkUseCaseIterative3 {
    private final IConceptRepository conceptRepository;
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\\[\\[(.+?)]]");


    public GetNetworkUseCaseIterative3(IConceptRepository conceptRepository) {
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
        Matcher matcher = DELIMITER_PATTERN.matcher(input);

        while (matcher.find()) {
            if (matcher.groupCount() >= 1) {
                results.add(matcher.group(1));
            }
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
