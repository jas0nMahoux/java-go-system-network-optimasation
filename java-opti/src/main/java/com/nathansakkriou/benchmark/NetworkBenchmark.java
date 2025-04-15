package com.nathansakkriou.benchmark;

import com.nathansakkriou.*;
import com.nathansakkriou.repository.ConceptRepositoryJson;
import com.nathansakkriou.repository.IConceptRepository;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class NetworkBenchmark {

    private GetNetworkUseCaseIterative0 iterative0;
    private GetNetworkUseCaseIterative0m1 iterative0m1;
    private GetNetworkUseCaseIterative1 iterative1;
    private GetNetworkUseCaseIterative1m1 iterative1m1;
    private GetNetworkUseCaseIterative2 iterative2;

    @Setup(Level.Invocation)
    public void setUp() {
        IConceptRepository conceptRepository = IConceptRepository.createInstanceJson100();
        iterative0 = new GetNetworkUseCaseIterative0(conceptRepository);
        iterative0m1 = new GetNetworkUseCaseIterative0m1(conceptRepository);
        iterative1 = new GetNetworkUseCaseIterative1(conceptRepository);
        iterative1m1 = new GetNetworkUseCaseIterative1m1(conceptRepository);
        iterative2 = new GetNetworkUseCaseIterative2(conceptRepository);
    }


    @Benchmark
    public void benchmarkIterative0() {
        iterative0.execute();
    }

    @Benchmark
    public void benchmarkIterative0m1() {
        iterative0m1.execute();
    }

    @Benchmark
    public void benchmarkIterative1() {
        iterative1.execute();
    }

    @Benchmark
    public void benchmarkIterative1m1() {
        iterative1m1.execute();
    }

    @Benchmark
    public void benchmarkIterative2() {
        iterative2.execute();
    }
}