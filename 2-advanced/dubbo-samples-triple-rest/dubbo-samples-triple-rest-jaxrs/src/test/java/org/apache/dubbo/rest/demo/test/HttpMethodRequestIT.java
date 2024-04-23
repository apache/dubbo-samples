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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rest.demo.routine.HttpMethodRequestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HttpMethodRequestIT {
    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");

    @DubboReference
    private HttpMethodRequestService httpMethodRequestService;

    @Test
    public void test() {
        String result = httpMethodRequestService.sayHelloPut("world");
        Assert.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloGet("world");
        Assert.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloPut("world");
        Assert.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloDelete("world");
        Assert.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloPatch("world");
        Assert.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloOptions("world");
        Assert.assertEquals("Hello world", result);

        result = httpMethodRequestService.sayHelloHead("world");
        Assert.assertEquals("Hello world", result);
    }

    @Test
    public void testGet() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/HttpRequestMethod/sayGet?name=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testPost() throws JsonProcessingException {
        String value = new ObjectMapper().writeValueAsString("world");
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.post()
                .uri("http://" + providerAddress + ":50052/HttpRequestMethod/sayPost")
                .header("Content-type", "application/json")
                .body(value)
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testDel() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.delete()
                .uri("http://" + providerAddress + ":50052/HttpRequestMethod/sayDelete?name=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testHeader() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Void> result = defaultClient.head()
                .uri("http://" + providerAddress + ":50052/HttpRequestMethod/sayHead?name=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toBodilessEntity();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testPatch() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.patch()
                .uri("http://" + providerAddress + ":50052/HttpRequestMethod/sayPatch?name=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testOptions() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.options()
                .uri("http://" + providerAddress + ":50052/HttpRequestMethod/sayOptions?name=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("Hello world", result.getBody());
    }


    @Test
    public void testPut() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.put()
                .uri("http://" + providerAddress + ":50052/HttpRequestMethod/sayPut?name=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("Hello world", result.getBody());
    }

}
