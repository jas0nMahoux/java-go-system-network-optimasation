package com.nathansakkriou.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nathansakkriou.entities.concept.Concept;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConceptRepositoryJson implements IConceptRepository{
    private final List<Concept> concepts = new ArrayList<>();

    public ConceptRepositoryJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        List<Concept> fileConcept;
        try {
            fileConcept = mapper.readValue(new File(filePath), new TypeReference<List<Concept>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            fileConcept = new ArrayList<>();
        }

        concepts.addAll(fileConcept);
    }

    @Override
    public List<Concept> findAll() {
        return concepts;
    }
}
