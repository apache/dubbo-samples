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

package org.apache.dubbo.rest.demo.test;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rest.demo.DemoService;
import org.apache.dubbo.rest.demo.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.stream.IntStream;

@EnableDubbo
@RunWith(SpringRunner.class)
public class ConsumerIT {

    private static final String HOST = System.getProperty("dubbo.address", "localhost");

    private static final StringBuffer finalString = new StringBuffer();

    static {
        IntStream.range(0, 20000).forEach(i -> finalString.append(i).append("Hello"));
    }

    private final RestClient restClient = RestClient.create();

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private DemoService demoService;

    private static String toUri(String path) {
        return "http://" + HOST + ":50052/org.apache.dubbo.rest.demo.DemoService" + path;
    }

    @Test
    public void helloWithRpc() {
        String result = demoService.hello("world");
        Assert.assertEquals(finalString + "Hello world", result);
    }

    @Test
    public void helloWithRest() {
        String result = restClient.get().uri(toUri("/hello?name=world")).retrieve().body(String.class);
        Assert.assertEquals("\"" + finalString + "Hello world\"", result);
    }

    @Test
    public void helloWithRestAdvance() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("name", "Yang");

        String result = restClient.post()
                .uri(toUri("/hi.txt?title=Mr"))
                .body(data)
                .header("c", "3")
                .retrieve()
                .body(String.class);
        Assert.assertEquals(finalString + "Hello Mr. Yang, 3", result);
    }

    @Test
    public void helloWithBody() {
        User user = new User();
        user.setTitle("Mr");
        user.setName("Yang");

        String result = restClient.post()
                .uri(toUri("/helloUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve()
                .body(String.class);
        Assert.assertEquals("\"" + finalString + "Hello Mr. Yang\"", result);
    }
}
