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
import org.apache.dubbo.rest.demo.routine.HttpMethodRequestService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HttpMethodRequestIT extends BaseTest {

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private HttpMethodRequestService httpMethodRequestService;

    @Test
    public void test() {
        String result = httpMethodRequestService.sayHelloPut("world");
        Assertions.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloGet("world");
        Assertions.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloPut("world");
        Assertions.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloDelete("world");
        Assertions.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloPatch("world");
        Assertions.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloOptions("world");
        Assertions.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloHead("world");
        Assertions.assertEquals("Hello world", result);
    }

    @Test
    public void testGet() {
        ResponseEntity<String> result = restClient.get()
                .uri(toUri("/HttpRequestMethod/sayGet?name=world"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testPost() throws JsonProcessingException {
        String value = new ObjectMapper().writeValueAsString("world");
        ResponseEntity<String> result = restClient.post()
                .uri(toUri("/HttpRequestMethod/sayPost"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testDel() {
        ResponseEntity<String> result = restClient.delete()
                .uri(toUri("/HttpRequestMethod/sayDelete?name=world"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testHeader() {
        ResponseEntity<Void> result = restClient.head()
                .uri(toUri("/HttpRequestMethod/sayHead?name=world"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toBodilessEntity();

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testPatch() {
        ResponseEntity<String> result = restClient.patch()
                .uri(toUri("/HttpRequestMethod/sayPatch?name=world"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testOptions() {
        ResponseEntity<String> result = restClient.options()
                .uri(toUri("/HttpRequestMethod/sayOptions?name=world"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testPut() {
        ResponseEntity<String> result = restClient.put()
                .uri(toUri("/HttpRequestMethod/sayPut?name=world"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("Hello world", result.getBody());
    }

}
