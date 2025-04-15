package com.nathansakkriou.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nathansakkriou.entities.concept.Concept;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConceptRepository implements IConceptRepository{

    public ConceptRepository() {
    }

    public List<Concept> loadConcepts() {
        List<Concept> concepts = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("./data/30.json"), new TypeReference<List<Concept>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Concept create(String title, String description) {
        return new Concept(
                UUID.randomUUID(),
                title,
                description
        );
    }

    @Override
    public List<Concept> findAll() {
        return List.of();
    }
}
