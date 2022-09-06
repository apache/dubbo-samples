/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.dubbo.sample.tri;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.CountDownLatch;

public class GrpcBenchmarkTest {

    @Test
    public void runBenchmark() throws RunnerException, InterruptedException {
        Options build = new OptionsBuilder()
                .include(RunBenchMark.class.getSimpleName())
                .build();
        new Runner(build).run();
    }

    @Test
    public void runTriBenchmark() throws RunnerException, InterruptedException {
        Options build = new OptionsBuilder()
                .include(RunTriBenchMark.class.getSimpleName())
                .build();
        new Runner(build).run();
    }

    private volatile boolean running = true;

    @Test
    public void runProfiler() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            RunBenchMark runBenchMark = new RunBenchMark();
            while(running) {
                runBenchMark.test();
            }
            countDownLatch.countDown();
        }).start();

        Thread.sleep(60000);
        running = false;
        countDownLatch.await();
    }

    @State(Scope.Benchmark)
    public static class RunBenchMark {

        ManagedChannel channel;
        GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

        protected GreeterRequest request = GreeterRequest.newBuilder()
                .setName("name")
                .build();

        public RunBenchMark() {
            this.channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9091).usePlaintext().build();
            this.greeterBlockingStub = GreeterGrpc.newBlockingStub(channel);
        }
        @Benchmark
        public void test() {
            this.greeterBlockingStub.greet(request);
        }

        @Test
        public void mytest() {
            GreeterReply greet = greeterBlockingStub.greet(request);
            Assert.assertNotNull(greet);
        }
    }

    @State(Scope.Benchmark)
    public static class RunTriBenchMark {

        ManagedChannel channel;
        GreeterGrpc.GreeterBlockingStub greeterBlockingStub;

        protected GreeterRequest request = GreeterRequest.newBuilder()
                .setName("name")
                .build();

        public RunTriBenchMark() {
            this.channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50053).usePlaintext().build();
            this.greeterBlockingStub = GreeterGrpc.newBlockingStub(channel);
        }
        @Benchmark
        public void test() {
            this.greeterBlockingStub.greet(request);
        }

        @Test
        public void mytest() {
            GreeterReply greet = greeterBlockingStub.greet(request);
            Assert.assertNotNull(greet);
        }
    }
}
