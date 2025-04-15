package com.nathansakkriou.entities.network;

import com.nathansakkriou.entities.concept.Concept;

import java.util.List;


public record NetworkConcepts(
    List<Concept> nodes,
    List<Edge> edges) {
}
