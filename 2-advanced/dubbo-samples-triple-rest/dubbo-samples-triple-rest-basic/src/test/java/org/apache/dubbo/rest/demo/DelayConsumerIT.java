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

package org.apache.dubbo.rest.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;

/**
 * delay to test connection reset issue triggered by first case
 */
@EnableDubbo
@ExtendWith(SpringExtension.class)
public class DelayConsumerIT {

    private static final String HOST = System.getProperty("dubbo.address", "localhost");

    private static final StringBuffer finalString = new StringBuffer();

    static {
        IntStream.range(0, 20000).forEach(i -> finalString.append(i).append("Hello"));
    }

    private static String toUri(String path) {
        return "http://" + HOST + ":50052/org.apache.dubbo.rest.demo.DemoService" + path;
    }

    @BeforeAll
    public static void init() throws InterruptedException {
        System.out.println("delay 2s to test connection reset issue triggered by first case");
        Thread.sleep(2000);
    }

    @Test
    public void helloWithBody() {
        RestClient restClient = RestClient.create();

        User user = new User();
        user.setTitle("Mr");
        user.setName("Yang");

        String result = restClient.post()
                .uri(toUri("/helloUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve()
                .body(String.class);
        Assertions.assertEquals("\"" + finalString + "Hello Mr. Yang\"", result);
    }
}
