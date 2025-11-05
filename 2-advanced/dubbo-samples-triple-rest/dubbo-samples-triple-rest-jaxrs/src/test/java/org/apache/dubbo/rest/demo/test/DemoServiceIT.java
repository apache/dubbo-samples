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
import org.apache.dubbo.rest.demo.DemoService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

public class DemoServiceIT extends BaseTest {

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private DemoService demoService;

    @Test
    public void test() {
        String result = demoService.hello("world");
        Assertions.assertEquals("Hello world", result);

        String res = demoService.deleteUserById("1");
        Assertions.assertEquals("1", res);

        int userById = demoService.findUserById(1);
        Assertions.assertEquals(1, userById);

        Long formBody = demoService.testFormBody(1L);
        Assertions.assertEquals(Long.valueOf(1), formBody);

    }

    @Test
    public void testRest() {
        ResponseEntity<String> result = restClient.get()
                .uri(toUri("/demo/hello?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testQuery() {
        ResponseEntity<Integer> response = RestClient.create()
                .get()
                .uri(toUri("/demo/findUserById?id={id}"), 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Integer.class);
        Assertions.assertEquals(Integer.valueOf(1), response.getBody());
    }

    @Test
    public void testFrom() {
        MultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
        map.add("number", 1L);
        ResponseEntity<Long> response = RestClient.create()
                .post()
                .uri(toUri("/demo/form"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(Long.class);
        Assertions.assertEquals(Long.valueOf(1), response.getBody());
    }

    @Test
    public void testDel() {
        ResponseEntity<String> response = RestClient.create()
                .delete()
                .uri(toUri("/demo/deleteUserById/1"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("1", response.getBody());
    }

}
