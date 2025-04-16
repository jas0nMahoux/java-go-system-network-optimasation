package com.nathansakkriou;

import com.nathansakkriou.entities.concept.Concept;
import com.nathansakkriou.entities.network.Edge;
import com.nathansakkriou.entities.network.NetworkConcepts;
import com.nathansakkriou.repository.IConceptRepository;

import java.util.*;
import java.util.concurrent.Executors;

/**
 * Optimisation de la méthode de recherche de delimiter [[]]
 */
public class GetNetworkUseCaseIterative5 {
    private final IConceptRepository conceptRepository;

    public GetNetworkUseCaseIterative5(IConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    /**
     * Exécute la récupération du réseau de concepts
     *
     * @param showDeleted indique si les concepts supprimés doivent être inclus
     * @return un objet NetworkConcepts contenant tous les concepts et les liens entre eux
     */
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

    /**
     * Extrait le texte entre les délimiteurs [[ et ]]
     *
     * @param input la chaîne d'entrée
     * @return une liste de chaînes extraites entre les délimiteurs
     */
    private Set<String> extractTextBetweenDelimiters(String input) {
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

    /**
     * Transforme une liste de concepts en une map avec le titre du concept comme clé et son UUID comme valeur
     *
     * @param concepts la liste de concepts à transformer
     * @return une map avec le titre du concept comme clé et son UUID comme valeur
     */
    private Map<String, UUID> conceptsToMapTitleUuid(List<Concept> concepts) {
        Map<String, UUID> mapTitleId = new HashMap<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (Concept concept : concepts)
                executor.execute(() -> {
                    mapTitleId.put(concept.getTitle(), concept.getId());
                });
        }
        return mapTitleId;
    }
}
