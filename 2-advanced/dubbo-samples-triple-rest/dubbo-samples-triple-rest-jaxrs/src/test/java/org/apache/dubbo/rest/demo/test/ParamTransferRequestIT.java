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
import org.apache.dubbo.rest.demo.routine.ParamTransferRequestService;
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
public class ParamTransferRequestIT {

    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");


    @DubboReference
    private ParamTransferRequestService paramTransferRequestService;

    @Test
    public void test(){
        String result1 = paramTransferRequestService.sayHello("world");
        Assert.assertEquals("Hello world",result1);

        String result2 = paramTransferRequestService.sayForm("world");
        Assert.assertEquals("Hello world",result2);

        String result3 = paramTransferRequestService.sayPath("1");
        Assert.assertEquals("Hello 1",result3);

        String result4 = paramTransferRequestService.sayHeader("world");
        Assert.assertEquals("Hello world",result4);

        String result5 = paramTransferRequestService.sayCookie("1");
        Assert.assertEquals("Hello 1",result5);
    }


    @Test
    public void testQuery(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/query?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }

    @Test
    public void testPath(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/path/1")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello 1",response.getBody());
    }

    @Test
    public void testFrom(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("form","world");
        ResponseEntity<String> response = RestClient.create().post()
                .uri("http://" + providerAddress + ":50052/param/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world", response.getBody());

    }

    @Test
    public void testHeader(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/header")
                .header( "Content-type","text/plain")
                .header("name","world")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }


    @Test
    public void testCookie(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/cookie")
                .header( "Content-type","text/plain")
                .header( "Cookie","cookieId=1")
                .retrieve()
                .toEntity(String.class);
        System.out.println(response.getBody());
    }

}
