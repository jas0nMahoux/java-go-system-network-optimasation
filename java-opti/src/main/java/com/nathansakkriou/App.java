package com.nathansakkriou;

import com.nathansakkriou.benchmark.NetworkBenchmark;
import com.nathansakkriou.benchmark.TestNetworkBenchmark;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class App
{
    public static void main( String[] args ) throws RunnerException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new java.util.Date());
        System.out.println(timeStamp);

        Options opt = new OptionsBuilder()
                .include(NetworkBenchmark.class.getSimpleName())
                .forks(1)
                .resultFormat(ResultFormatType.JSON)
                .result("reports/benchmark-" + timeStamp + ".json")
                .build();



        new Runner(opt).run();
    }
}
