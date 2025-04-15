package entities.network;

import entities.concept.Concept;

import java.util.UUID;

public record Edge (
    UUID source,
    UUID target) {

    public static Edge fromTargetAndSource(Concept target, Concept source) {
        return new Edge(source.id(), target.id());
    }
}
