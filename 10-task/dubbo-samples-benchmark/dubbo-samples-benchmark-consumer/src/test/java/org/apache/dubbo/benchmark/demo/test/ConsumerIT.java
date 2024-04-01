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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.io.FileUtils;
import org.apache.dubbo.benchmark.demo.DemoService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ConsumerIT {

    String propKey = "prop";

    @Test
    public void test() throws RunnerException {

        String prop = System.getProperty(propKey);
        String propJson = null;

        if (StringUtils.isNotBlank(prop)) {
            prop = prop.replace("\"", "");
            String[] props = prop.split(" ");

            Map<String, String> propMap = new HashMap<>();
            List<String> propList = new ArrayList<>();
            for (String p : props) {
                p = p.substring(2);
                String key = p.substring(0, p.indexOf("="));
                String val = p.substring(p.indexOf("=") + 1);
                propMap.put(key, val);
                propList.add(p);
            }
            propJson = new Gson().toJson(propMap);
            prop = String.join("_", propList);
        }

        Options options;
        ChainedOptionsBuilder optBuilder = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .param("time", System.currentTimeMillis() + "")
                .param("prop", propJson == null ? "" : propJson)
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(1))
                .measurementIterations(1)
                .measurementTime(TimeValue.seconds(1))
                .mode(Mode.SingleShotTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .threads(Threads.MAX)
                .forks(0);

        options = doOptions(optBuilder, prop).build();
        new Runner(options).run();

        dotTrace(prop, propKey);

    }

    private static void dotTrace(String prop, String propKey) {
        String url = "jdbc:mysql://bh-mysql:3306/skywalking?useSSL=false";
        String user = "root";
        String password = "123456";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            String sql = "SELECT data_binary FROM segment order by start_time desc limit 1";
            resultSet = statement.executeQuery(sql);

            String dataBinary = null;
            if (resultSet.next()) {
                dataBinary = resultSet.getString("data_binary");
                System.out.println("dataBinary: " + dataBinary);
            }

            Class<?> segmentObjectClass = Class.forName("org.apache.skywalking.apm.network.language.agent.v3.SegmentObject");

            Object segmentObject = new Object();
            if (StringUtils.isNotBlank(dataBinary)) {
                byte[] bytes = Base64.getDecoder().decode(dataBinary);
                segmentObject = segmentObjectClass.getDeclaredMethod("parseFrom", byte[].class).invoke(null, bytes);
            }

            String traceFileName;
            if (StringUtils.isNotBlank(prop)) {
                traceFileName = "/tmp/jmh_trace_prop[" + prop + "].json";
            } else {
                traceFileName = "/tmp/jmh_trace.json";
            }

            String traceJson;
            Gson gson = new Gson();
            if (StringUtils.isNotBlank(prop)) {
                JsonElement jsonElement = gson.toJsonTree(segmentObject);
                jsonElement.getAsJsonObject().addProperty(propKey, prop);
                traceJson = gson.toJson(jsonElement);
            } else {
                traceJson = gson.toJson(segmentObject);
            }

            traceJson = "[" + traceJson + "]";

            FileUtils.write(new File(traceFileName), traceJson, Charset.defaultCharset(), false);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("mysql test error");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
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

        private final DemoService service;

        public MyBenchmark() {
            String zkAddr = System.getProperty("zookeeper.address", "127.0.0.1");
            ReferenceConfig<DemoService> reference =
                    ReferenceBuilder.<DemoService>newBuilder()
                            .interfaceClass(DemoService.class)
                            .addRegistry(new RegistryConfig("zookeeper://" + zkAddr + ":2181"))
                            .build();
            DubboBootstrap bootstrap = DubboBootstrap.getInstance();
            bootstrap.application("dubbo-benchmark-consumer");
            bootstrap.reference(reference).start();
            service = reference.get();
        }

        @Param({""})
        private String time;

        @Param({""})
        private String prop;

        @Benchmark
        public String getUser() {
            return service.sayHello("dubbo");
        }

    }

}
