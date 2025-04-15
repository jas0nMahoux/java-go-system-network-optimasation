package com.nathansakkriou;

import com.nathansakkriou.entities.concept.Concept;
import com.nathansakkriou.entities.network.Edge;
import com.nathansakkriou.entities.network.NetworkConcepts;
import com.nathansakkriou.repository.IConceptRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Version naive + Parallele sur les streams de recherche
 */
public class GetNetworkUseCaseIterative0m1 {
    private final IConceptRepository conceptRepository;

    public GetNetworkUseCaseIterative0m1(IConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    public NetworkConcepts execute() {
        List<Edge> edges = new ArrayList<>();
        List<Concept> allConcept = conceptRepository.findAll();

        for (Concept concept : allConcept)
            edges.addAll(findEdges(concept, allConcept));

        return new NetworkConcepts(allConcept, edges);
    }

    private List<Edge> findEdges(Concept concept, List<Concept> allConcept) {
        List<String> refs = extractTextBetweenDelimiters(concept.description());
        List<Edge> edges = new ArrayList<>();
        refs.forEach(ref -> {
            var filterList = allConcept.stream()
                    .parallel()
                    .filter(concept1 -> concept1.id().toString().equals(ref)).toList();

            if(!filterList.isEmpty()) {
                edges.add(new Edge(concept.id(), filterList.get(0).id()));

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
