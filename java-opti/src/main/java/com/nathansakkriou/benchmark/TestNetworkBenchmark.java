package com.nathansakkriou.benchmark;

import com.nathansakkriou.*;
import com.nathansakkriou.repository.IConceptRepository;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class TestNetworkBenchmark {

    private GetNetworkUseCaseIterative2 iterative2;

    @Setup(Level.Invocation)
    public void setUp() {
        IConceptRepository conceptRepository = IConceptRepository.createInstanceJson100();
        iterative2 = new GetNetworkUseCaseIterative2(conceptRepository);
    }

    @Benchmark
    public void benchmarkIterative2() {
        iterative2.execute(false);
    }
}