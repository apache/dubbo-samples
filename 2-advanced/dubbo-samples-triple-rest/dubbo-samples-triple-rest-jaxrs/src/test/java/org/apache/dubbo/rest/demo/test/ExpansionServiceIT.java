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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExpansionServiceIT {

    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");


    @Test
    public void testFilter(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/ext/filter?name={name}","world ")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world response-filter",response.getBody());

    }

    @Test
    public void testIntercept(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/ext/intercept?name={name}","world ")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world intercept",response.getBody());
    }


    @Test
    public void testException(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/ext/exception")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("test-exception",response.getBody());
    }



}
