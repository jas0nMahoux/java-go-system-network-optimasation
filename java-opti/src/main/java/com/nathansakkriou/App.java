package com.nathansakkriou;

import com.nathansakkriou.entities.concept.Concept;
import com.nathansakkriou.repository.ConceptRepository;
import com.nathansakkriou.repository.IConceptRepository;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        IConceptRepository conceptRepository = new ConceptRepository();
        List<Concept> allConcepts = conceptRepository.findAll();

        GetNetworkUseCase usecase = new GetNetworkUseCase(conceptRepository);

        var network = usecase.execute();
        System.out.println(network);
    }

}
