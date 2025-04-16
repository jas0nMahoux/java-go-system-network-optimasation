package com.nathansakkriou;

import com.nathansakkriou.entities.concept.Concept;
import com.nathansakkriou.entities.network.Edge;
import com.nathansakkriou.entities.network.NetworkConcepts;
import com.nathansakkriou.repository.IConceptRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Version naive
 */
public class GetNetworkUseCaseIterative0 {
    private final IConceptRepository conceptRepository;

    public GetNetworkUseCaseIterative0(IConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    public NetworkConcepts execute(boolean showDeleted) {
        List<Edge> edges = new ArrayList<>();
        List<Concept> allConcept = showDeleted ? conceptRepository.findAll() : conceptRepository.findAllExcludeDeleted();

        for (Concept concept : allConcept)
            edges.addAll(findEdges(concept, allConcept));

        return new NetworkConcepts(allConcept, edges);
    }

    private List<Edge> findEdges(Concept concept, List<Concept> allConcept) {
        List<String> refs = extractTextBetweenDelimiters(concept.getDescription());
        List<Edge> edges = new ArrayList<>();
        refs.forEach(ref -> {
            var filterList = allConcept.stream().filter(concept1 -> concept1.getId().toString().equals(ref)).toList();
            if(!filterList.isEmpty()) {
                edges.add(new Edge(concept.getId(), filterList.get(0).getId()));

            }
        });
        return edges;
    }

    private static List<String> extractTextBetweenDelimiters(String input) {
        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[\\[(.+?)]]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (matcher.groupCount() >= 1) {
                results.add(matcher.group(1));
            }
        }

        return results;
    }
}
