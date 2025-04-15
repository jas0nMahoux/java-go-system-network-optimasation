package com.nathansakkriou.entities.network;

import com.nathansakkriou.entities.concept.Concept;

import java.util.UUID;

public record Edge (
    UUID source,
    UUID target) {

    public static Edge fromTargetAndSource(Concept target, Concept source) {
        return new Edge(source.id(), target.id());
    }
}
