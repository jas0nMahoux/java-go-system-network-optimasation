package com.nathansakkriou;

import com.nathansakkriou.entities.concept.Concept;
import com.nathansakkriou.entities.network.Edge;
import com.nathansakkriou.entities.network.NetworkConcepts;
import com.nathansakkriou.repository.IConceptRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GetNetworkUseCase {
    private final IConceptRepository conceptRepository;

    public GetNetworkUseCase(IConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    public NetworkConcepts execute() {
        List<Concept> allConcept = conceptRepository.findAll();
        Map<String, UUID> mapTitleId = conceptsToMapTitleUuid(allConcept);
        List<Edge> edges = new ArrayList<>();
        for (Concept concept : allConcept) {
            edges.addAll(
                    findEdges(concept, mapTitleId)
            );
        }
        return null;
    }

    private List<Edge> findEdges(Concept concept, Map<String, UUID> mapTitleId) {
        List<String> refs = extractTextBetweenDelimiters(concept.description());
        List<Edge> edges = new ArrayList<>();
        refs.forEach(ref -> {
            if (mapTitleId.containsKey(ref)) {
                UUID conceptId = mapTitleId.get(ref);
                edges.add(new Edge(concept.id(), conceptId));
            }
        });
        return edges;
    }

    private static List<String> extractTextBetweenDelimiters(String input) {
        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("[[(.+?)]]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            results.add(matcher.group(1)); // groupe 1 = texte entre [[ ]]
        }

        return results;
    }

    private Map<String, UUID> conceptsToMapTitleUuid(List<Concept> concepts) {
        return concepts.stream().collect(
                Collectors.toMap(Concept::title, Concept::id)
        );
    }
}
