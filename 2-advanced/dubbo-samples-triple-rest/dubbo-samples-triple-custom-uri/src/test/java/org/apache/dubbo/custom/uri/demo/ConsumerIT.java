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

package org.apache.dubbo.custom.uri.demo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@EnableDubbo
@RunWith(SpringRunner.class)
public class ConsumerIT {

    private static final String HOST = System.getProperty("dubbo.address", "localhost");
    private final RestClient webClient = RestClient.create();

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private Greeter greeter;

    private static String toUri(String path) {
        return "http://" + HOST + ":50052/org.apache.dubbo.custom.uri.demo.Greeter" + path;
    }

    @Test
    public void sayHelloWithPost() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("name", "He");

        String result = webClient.post()
                .uri(toUri("/v1/hello"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .body(String.class);
        Assert.assertEquals("{\"message\":\"Hello, He!\"}", result);
    }

    @Test
    public void updateGreetingWithPut() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("name", "He");

        String result = webClient.put()
                .uri(toUri("/v1/hello/update"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .body(String.class);
        Assert.assertEquals("{\"message\":\"Greeting updated for: He!\"}", result);
    }

    @Test
    public void healthCheck() {
        String result = webClient.put()
                .uri(toUri("/v1/hello/health"))
                .retrieve()
                .body(String.class);
        Assert.assertEquals("{\"message\":\"Health check successful!\"}", result);
    }

    @Test
    public void checkName() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("name", "He");

        String result = webClient.put()
                .uri(toUri("/v1/hello/check-name/heliang"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .body(String.class);
        Assert.assertEquals("{\"message\":\"Name checked: He\"}", result);
    }

    @Test
    public void simpleCheck() {
        // 创建一个 HelloRequest 对象，但不需要填充字段
        HelloRequest request = HelloRequest.newBuilder().build(); // 可以不设置任何字段

        String result = webClient.put()
                .uri(toUri("/v1/hello/simple-check"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request) // 发送 HelloRequest 对象
                .retrieve()
                .body(String.class);

        Assert.assertEquals("{\"message\":\"Simple check successful!\"}", result);
    }


    @Test
    public void actionCheck() {
        HelloRequest request = HelloRequest.newBuilder().build(); // 可以不设置任何字段
        String result = webClient.put() // 改为 PUT 方法
                .uri(toUri("/v1/hello/check")) // 使用新的路径
                .body(request)
                .retrieve()
                .body(String.class);
        Assert.assertEquals("{\"message\":\"Action check successful!\"}", result);
    }

    @Test
    public void actionCheckWithName() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("name", "He");

        String result = webClient.post()
                .uri(toUri("/v1/hello/check-with-name")) // 使用新的路径
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .body(String.class);

        Assert.assertEquals("{\"message\":\"Action check with name: He\"}", result);
    }
}
