/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.benchmark.demo.test;

import org.apache.dubbo.benchmark.demo.DemoService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerIT {

    private static final int CONCURRENCY = 32;
    private final static AtomicInteger counter = new AtomicInteger(0);

    @Test
    public void test() throws RunnerException {

        int warmupIterations =3;
        int warmupTime = 10;
        int measurementIterations = 3;
        int measurementTime = 10;

        Options options;
        ChainedOptionsBuilder optBuilder = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .warmupIterations(warmupIterations)
                .warmupTime(TimeValue.seconds(warmupTime))
                .measurementIterations(measurementIterations)
                .measurementTime(TimeValue.seconds(measurementTime))
                .threads(CONCURRENCY)
                .forks(1);

        options = doOptions(optBuilder).build();
        new Runner(options).run();

    }

    private static ChainedOptionsBuilder doOptions(ChainedOptionsBuilder optBuilder) {
        String output = System.getProperty("benchmark.output");
        if (output != null && !output.trim().isEmpty()) {
            optBuilder.result(output);
            optBuilder.resultFormat(ResultFormatType.JSON);
        }
        return optBuilder;
    }

    @State(Scope.Benchmark)
    public static class MyBenchmark {

        @Benchmark
        @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime})
        @OutputTimeUnit(TimeUnit.MILLISECONDS)
        public String getUser() {
            ReferenceConfig<DemoService> reference =
                    ReferenceBuilder.<DemoService>newBuilder()
                            .interfaceClass(DemoService.class)
                            .url("tri://localhost:20880")
                            .build();
            DubboBootstrap.getInstance().reference(reference).start();
            DemoService service = reference.get();

            return service.sayHello("dubbo");
        }
    }
}
