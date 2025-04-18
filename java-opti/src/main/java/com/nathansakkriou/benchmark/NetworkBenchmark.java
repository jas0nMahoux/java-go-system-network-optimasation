package com.nathansakkriou.benchmark;

import com.nathansakkriou.*;
import com.nathansakkriou.repository.ConceptRepositoryJson;
import com.nathansakkriou.repository.IConceptRepository;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;


@BenchmarkMode({Mode.AverageTime, Mode.Throughput, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class NetworkBenchmark {

    private GetNetworkUseCaseIterative0 iterative0;
    private GetNetworkUseCaseIterative0m1 iterative0m1;
    private GetNetworkUseCaseIterative1 iterative1;
    private GetNetworkUseCaseIterative1m1 iterative1m1;
    private GetNetworkUseCaseIterative2 iterative2;
    private GetNetworkUseCaseIterative4 iterative4;
    private GetNetworkUseCaseIterative3 iterative3;
    private GetNetworkUseCaseIterative5 iterative5;

    @Setup(Level.Invocation)
    public void setUp() {
        IConceptRepository conceptRepository = IConceptRepository.createInstanceJson100();
        iterative0 = new GetNetworkUseCaseIterative0(conceptRepository);
        iterative0m1 = new GetNetworkUseCaseIterative0m1(conceptRepository);
        iterative1 = new GetNetworkUseCaseIterative1(conceptRepository);
        iterative1m1 = new GetNetworkUseCaseIterative1m1(conceptRepository);
        iterative2 = new GetNetworkUseCaseIterative2(conceptRepository);
        iterative4 = new GetNetworkUseCaseIterative4(conceptRepository);
        iterative3 = new GetNetworkUseCaseIterative3(conceptRepository);
        iterative5 = new GetNetworkUseCaseIterative5(conceptRepository);
    }


    @Benchmark
    public void V0_Naive() {
        iterative0.execute(false);
    }

    @Benchmark
    public void V0_Naive_parallel() {
        iterative0m1.execute(false);
    }

    @Benchmark
    public void V1_Map() {
        iterative1.execute(false);
    }

    @Benchmark
    public void V1_Map_parallel() {
        iterative1m1.execute(false);
    }

    @Benchmark
    public void V2_Thread() {
        iterative2.execute(false);
    }

    @Benchmark
    public void V3_pattern_global() {
        iterative3.execute(false);
    }

    @Benchmark
    public void V4_search_delimiter() {
        iterative4.execute(false);
    }

    @Benchmark
    public void V5_virtual_thread() {
        iterative5.execute(false);
    }
}