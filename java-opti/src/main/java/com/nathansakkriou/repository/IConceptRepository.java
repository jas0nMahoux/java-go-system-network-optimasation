package com.nathansakkriou.repository;

import com.nathansakkriou.entities.concept.Concept;

import java.util.List;

public interface IConceptRepository {
    List<Concept> findAllExcludeDeleted();
    List<Concept> findAll();

    static IConceptRepository createInstanceJson100() {
        return new ConceptRepositoryJson("./data/100.json", 20);
    }

    static IConceptRepository createInstanceJson50() {
        return new ConceptRepositoryJson("./data/50.json", 20);
    }

    static IConceptRepository createInstanceJson150() {
        return new ConceptRepositoryJson("./data/150.json", 20);
    }
}
