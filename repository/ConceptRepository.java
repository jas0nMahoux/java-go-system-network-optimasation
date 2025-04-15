package repository;

import entities.concept.Concept;

import java.util.UUID;

public class ConceptRepository {

    public ConceptRepository() {
    }

    public Concept create(String title, String description) {
        return new Concept(
                UUID.randomUUID(),
                title,
                description
        );
    }

}
