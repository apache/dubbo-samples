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

import org.apache.commons.io.FileUtils;
import org.apache.dubbo.benchmark.demo.DemoService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class ConsumerIT {

    @Test
    public void test() throws RunnerException {

        int measurementTime = 10;
        String propKey = "prop";
        String prop = System.getProperty(propKey);

        if (StringUtils.isNotBlank(prop)) {
            prop = prop.replace("\"", "");
            //去掉前两位
            prop = prop.substring(2);
            propKey = prop.substring(0, prop.indexOf("="));
            //取=后面的val
            prop = prop.substring(prop.indexOf("=") + 1);
        }

        System.out.println("propKey:" + propKey);
        System.out.println("prop:" + prop);

        Options options;
        ChainedOptionsBuilder optBuilder = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
//                .param("time", System.currentTimeMillis() + "")
                .param("prop", prop)
                .measurementTime(TimeValue.seconds(measurementTime))
                .measurementIterations(3)
                .forks(1);

        options = doOptions(optBuilder, prop).build();
        new Runner(options).run();

        //把json文件的prop字段，替换成propKey
        if (StringUtils.isNotBlank(prop)) {
            String json;
            try {
                json = FileUtils.readFileToString(new File("/tmp/jmh_result_prop[" + prop + "].json"), "UTF-8");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            json = json.replace("prop", propKey);
            try {
                FileUtils.write(new File("/tmp/jmh_result_prop[" + prop + "].json"), json, Charset.defaultCharset(), false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private static ChainedOptionsBuilder doOptions(ChainedOptionsBuilder optBuilder, String prop) {
        if (StringUtils.isNotBlank(prop)) {
            optBuilder.result("/tmp/jmh_result_prop[" + prop + "].json");
        } else {
            optBuilder.result("/tmp/jmh_result.json");
        }
        optBuilder.resultFormat(ResultFormatType.JSON);
        return optBuilder;
    }

    @State(Scope.Benchmark)
    public static class MyBenchmark {

        @Param({""})
        private String time;

        @Param({""})
        private String prop;

        @Benchmark
        @BenchmarkMode({Mode.Throughput, Mode.AverageTime})
        @OutputTimeUnit(TimeUnit.MILLISECONDS)
        public String getUser() {
            String zkAddr = System.getProperty("zookeeper.address", "127.0.0.1");
            ReferenceConfig<DemoService> reference =
                    ReferenceBuilder.<DemoService>newBuilder()
                            .interfaceClass(DemoService.class)
                            .addRegistry(new RegistryConfig("zookeeper://" + zkAddr + ":2181"))
                            .build();
            DubboBootstrap bootstrap = DubboBootstrap.getInstance();
            bootstrap.application("dubbo-benchmark-consumer");
            bootstrap.reference(reference).start();
            DemoService service = reference.get();

            return service.sayHello("dubbo");
        }

    }

}
