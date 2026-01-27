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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class ExpansionServiceIT extends BaseTest {

    @Test
    public void testFilter() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/ext/filter?name={name}"), "world ")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world response-filter", response.getBody());

    }

    @Test
    public void testIntercept() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/ext/intercept?name={name}"), "world ")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world intercept", response.getBody());
    }

    @Test
    public void testException() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/ext/exception"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("test-exception", response.getBody());
    }

}
