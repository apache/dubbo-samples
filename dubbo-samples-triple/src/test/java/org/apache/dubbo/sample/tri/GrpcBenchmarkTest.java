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
}
