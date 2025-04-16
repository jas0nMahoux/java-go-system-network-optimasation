package com.nathansakkriou.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nathansakkriou.entities.concept.Concept;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConceptRepositoryJson implements IConceptRepository{
    private final List<Concept> concepts = new ArrayList<>();

    public ConceptRepositoryJson(String filePath, int countDeleted) {
        ObjectMapper mapper = new ObjectMapper();
        List<Concept> fileConcept;
        try {
            fileConcept = mapper.readValue(new File(filePath), new TypeReference<List<Concept>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            fileConcept = new ArrayList<>();
        }

        concepts.addAll(fileConcept);

        setDeletedConcept(countDeleted);
    }

    private void setDeletedConcept(int countDeleted) {
        var random = new Random();
        for(int i = 0; i < countDeleted; i++) {
            int index = random.nextInt(concepts.size() -1);
            concepts.get(index).setDeleted(true);
        }
    }

    @Override
    public List<Concept> findAllExcludeDeleted() {
        return concepts.stream()
                .parallel()
                .filter(Concept::isDeleted).toList();
    }

    @Override
    public List<Concept> findAll() {
        return concepts;
    }
}
