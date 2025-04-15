package entities.network;

import entities.concept.Concept;

import java.util.List;


public record NetworkConcepts(
    List<Concept> nodes,
    List<Edge> edges) {
}
