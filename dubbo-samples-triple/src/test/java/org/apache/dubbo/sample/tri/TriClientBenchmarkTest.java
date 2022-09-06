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

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;
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

public class TriClientBenchmarkTest {

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
        protected Greeter delegate;

        protected DubboBootstrap appDubboBootstrap;

        protected GreeterRequest request = GreeterRequest.newBuilder()
                .setName("name")
                .build();

        public RunBenchMark() {
            ReferenceConfig<Greeter> ref = new ReferenceConfig<>();
            ref.setInterface(Greeter.class);
            ref.setProtocol(CommonConstants.TRIPLE);
            ref.setTimeout(10000);
            ref.setRetries(0);

            DubboBootstrap bootstrap = DubboBootstrap.getInstance();
            ApplicationConfig applicationConfig = new ApplicationConfig(
                    TriClientBenchmarkTest.class.getName());
            applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
            bootstrap.application(applicationConfig)
                    .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                    .reference(ref)
                    .start();

            delegate = ref.get();
            appDubboBootstrap = bootstrap;
        }
        @Benchmark
        public void test() {
            delegate.greet(request);
        }

        @Test
        public void mytest() {
            GreeterReply greet = delegate.greet(request);
            Assert.assertNotNull(greet);
        }
    }
}
