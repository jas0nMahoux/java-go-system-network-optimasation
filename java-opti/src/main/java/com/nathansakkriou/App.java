package com.nathansakkriou;

import com.nathansakkriou.benchmark.NetworkBenchmark;
import com.nathansakkriou.entities.concept.Concept;
import com.nathansakkriou.repository.ConceptRepository;
import com.nathansakkriou.repository.IConceptRepository;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NetworkBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();

        //        IConceptRepository conceptRepository = new ConceptRepository();
//        List<Concept> allConcepts = conceptRepository.findAll();
//
//        GetNetworkUseCaseIterative1 usecase = new GetNetworkUseCaseIterative1(conceptRepository);
//
//        var network = usecase.execute();
//        System.out.println(network);
    }

}
