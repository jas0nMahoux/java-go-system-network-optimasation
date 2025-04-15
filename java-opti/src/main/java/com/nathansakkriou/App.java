package com.nathansakkriou;

import com.nathansakkriou.repository.ConceptRepository;
import com.nathansakkriou.repository.IConceptRepository;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        IConceptRepository conceptRepository = new ConceptRepository();
        GetNetworkUseCase usecase = new GetNetworkUseCase(conceptRepository);

        var network = usecase.execute();
        System.out.println(network);
    }

}
