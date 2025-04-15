package com.nathansakkriou.repository;

import com.nathansakkriou.entities.concept.Concept;

import java.util.List;

public interface IConceptRepository {
    List<Concept> findAll();
}
