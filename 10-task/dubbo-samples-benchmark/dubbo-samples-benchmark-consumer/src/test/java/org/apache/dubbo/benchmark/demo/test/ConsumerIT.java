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
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ConsumerIT {

    String propKey = "prop";

    @Test
    public void test() throws Exception {

        String prop = System.getProperty(propKey);
        String propJson = null;

        if (StringUtils.isNotBlank(prop)) {
            prop = prop.replace("\"", "");
            String[] props = prop.split("\\|");

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

        String sampleFileName = runSample(propJson, prop);

        dotTrace(prop, propKey, propJson);

        String throughputFileName = runThroughput(propJson);

        mergeResult(sampleFileName, throughputFileName);

    }

    private static void mergeResult(String sampleFileName, String throughputFileName) throws IOException {
        String sampleResultJson = FileUtils.readFileToString(new File(sampleFileName), "UTF-8");
        String throughputResultJson = FileUtils.readFileToString(new File(throughputFileName), "UTF-8");

        Gson gson = new Gson();
        List firstResults = gson.fromJson(sampleResultJson, List.class);
        List secondResults = gson.fromJson(throughputResultJson, List.class);

        firstResults.addAll(secondResults);

        String mergedResultJson = gson.toJson(firstResults);
        FileUtils.writeStringToFile(new File(sampleFileName), mergedResultJson, "UTF-8");
    }

    private String runSample(String propJson, String prop) throws Exception {
        String fileName;
        if (StringUtils.isNotBlank(prop)) {
            fileName = "/tmp/jmh_result_prop[" + prop + "]_" + UUID.randomUUID() + ".json";
        } else {
            fileName = "/tmp/jmh_result_" + UUID.randomUUID() + ".json";
        }

        Options options = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .param("time", System.currentTimeMillis() + "")
                .param("prop", propJson == null ? "" : propJson)
                .resultFormat(ResultFormatType.JSON)
                .result(fileName)
                .mode(Mode.SampleTime)
                .threads(32)
                .forks(1)
                .build();

        new Runner(options).run();

        return fileName;
    }

    private static String runThroughput(String propJson) throws Exception {
        String fileName = "/tmp/" + UUID.randomUUID() + ".json";

        Options options = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .param("time", System.currentTimeMillis() + "")
                .param("prop", propJson == null ? "" : propJson)
                .resultFormat(ResultFormatType.JSON)
                .result(fileName)
                .jvmArgs("-Dzookeeper.address=zookeeper")
                .mode(Mode.Throughput)
                .threads(32)
                .forks(1)
                .build();

        new Runner(options).run();

        return fileName;
    }

    private static void dotTrace(String prop, String propKey, String propJson) {
        String url = "jdbc:mysql://bh-mysql:3306/skywalking?useSSL=false";
        String user = "root";
        String password = "123456";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String dataBinary = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            String setOffsetSql = "SELECT FLOOR(COUNT(*) * 0.01) FROM segment";
            statement = connection.prepareStatement(setOffsetSql);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int offset = resultSet.getInt(1);
                System.out.println("offset:" + offset);

                String querySql = "SELECT data_binary FROM segment ORDER BY latency DESC LIMIT 1 OFFSET ?";
                statement = connection.prepareStatement(querySql);
                statement.setInt(1, offset);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    dataBinary = resultSet.getString("data_binary");
                    System.out.println(dataBinary);
                }
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
            if (StringUtils.isNotBlank(propJson)) {
                JsonElement jsonElement = gson.toJsonTree(segmentObject);
                jsonElement.getAsJsonObject().addProperty(propKey, propJson);
                traceJson = gson.toJson(jsonElement);
            } else {
                traceJson = gson.toJson(segmentObject);
            }

            traceJson = "[" + traceJson + "]";

            FileUtils.write(new File(traceFileName), traceJson, Charset.defaultCharset(), false);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("dotTrace error");
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
