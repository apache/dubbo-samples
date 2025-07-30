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
import org.apache.dubbo.rest.demo.routine.MappingRequestService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class MappingRequestServiceIT extends BaseTest {

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private MappingRequestService mappingRequestService;

    @Test
    public void test() {
        String result1 = mappingRequestService.testService("world");
        Assertions.assertEquals("Hello world", result1);

        String result2 = mappingRequestService.testInterface("world");
        Assertions.assertEquals("Hello world", result2);

        String result3 = mappingRequestService.testPathOne("world");
        Assertions.assertEquals("Hello world", result3);

        int result4 = mappingRequestService.testPathInt(1);
        Assertions.assertEquals(1, result4);

        String result5 = mappingRequestService.testPathParam("a", "b");
        Assertions.assertEquals("ab", result5);

        String result6 = mappingRequestService.testPathTwo("world");
        Assertions.assertEquals("Hello world", result6);

        String result7 = mappingRequestService.testPathParamTwo("a", "b");
        Assertions.assertEquals("ab", result7);

        String result8 = mappingRequestService.testPathZero("world");
        Assertions.assertEquals("Hello world", result8);

        String result9 = mappingRequestService.testPathAny("world");
        Assertions.assertEquals("Hello world", result9);

        String result10 = mappingRequestService.testConsumesAJ("world");
        Assertions.assertEquals("Hello world", result10);

        String result11 = mappingRequestService.testConsumesAll("world");
        Assertions.assertEquals("Hello world", result11);

        String result12 = mappingRequestService.testProducesAJ("world");
        Assertions.assertEquals("Hello world", result12);

        String result13 = mappingRequestService.testProducesAll("world");
        Assertions.assertEquals("Hello world", result13);
    }

    @Test
    public void testInterfacePath() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/path?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testServicePath() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/servicePath?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testPathZero() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testPathOne() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/say/?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testPathTwo() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/library/books?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testPathParamTwo() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/library/{isbn}/{type}"), "ISN1111", "CHINESE")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("ISN1111CHINESE", response.getBody());
    }

    @Test
    public void testPathParam() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/foo{name}-{zip}bar"), "F", "G")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("FG", response.getBody());
    }

    @Test
    public void testPathInt() {
        ResponseEntity<Integer> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/1/stuff"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Integer.class);
        Assertions.assertEquals(Integer.valueOf(1), response.getBody());
    }

    @Test
    public void testPathAny() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/{name}/stuff"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testConsumeAj() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/consumeAj?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testConsumeAll() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/consumeAll?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testProducesAJ() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/producesAJ?name={name}"), "world")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("\"Hello world\"", response.getBody());
    }

    @Test
    public void testProducesAll() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/mapping/producesAll?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

}
