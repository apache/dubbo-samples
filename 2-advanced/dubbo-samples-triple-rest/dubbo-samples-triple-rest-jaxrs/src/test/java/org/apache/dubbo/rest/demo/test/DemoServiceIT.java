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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoServiceIT {

    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");

    @DubboReference
    private DemoService demoService;

    @Test
    public void test() {
        String result = demoService.hello(1, "world");
        Assert.assertEquals("Hello world1", result);

        String res = demoService.deleteUserById("1");
        Assert.assertEquals("1", res);

        int userById = demoService.findUserById(1);
        Assert.assertEquals(1, userById);

        Long formBody = demoService.testFormBody(1L);
        Assert.assertEquals(Long.valueOf(1),formBody);

    }

    @Test
    public void testRest() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/demo/hello?a={a}&name={name}",1,"world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world1", result.getBody());
    }

    @Test
    public void testQuery(){
        ResponseEntity<Integer> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/demo/findUserById?id={id}",1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Integer.class);
        Assert.assertEquals(Integer.valueOf(1), response.getBody());
    }

    @Test
    public void testFrom(){
        MultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
        map.add("number",1L);
        ResponseEntity<Long> response = RestClient.create().post()
                .uri("http://" + providerAddress + ":50052/demo/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(Long.class);
        Assert.assertEquals(Long.valueOf(1), response.getBody());
    }

    @Test
    public void testDel(){
        ResponseEntity<String> response = RestClient.create().delete()
                .uri("http://" + providerAddress + ":50052/demo/deleteUserById/1")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("1", response.getBody());
    }


}
