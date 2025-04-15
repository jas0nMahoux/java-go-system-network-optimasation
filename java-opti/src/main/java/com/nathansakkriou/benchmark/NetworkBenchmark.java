package com.nathansakkriou.benchmark;

import com.nathansakkriou.GetNetworkUseCaseIterative1;
import com.nathansakkriou.repository.ConceptRepository;
import com.nathansakkriou.repository.IConceptRepository;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class NetworkBenchmark {

    private GetNetworkUseCaseIterative1 iterative1;

    @Setup(Level.Invocation)
    public void setUp() {
        IConceptRepository conceptRepository = new ConceptRepository();
        iterative1 = new GetNetworkUseCaseIterative1(conceptRepository);
    }

    @Benchmark
    public void benchmarkIterative1() {
        iterative1.execute();
    }
}