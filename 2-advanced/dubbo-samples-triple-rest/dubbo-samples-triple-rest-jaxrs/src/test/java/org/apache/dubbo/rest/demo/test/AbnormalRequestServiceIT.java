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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClient;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AbnormalRequestServiceIT {

    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");


    @Test
    public void testNotFound(){
        RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/abnormal/not")
                .header("Content-type", "application/json")
                .exchange((request, response) -> {
                    Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
                    return response;
                });
    }

    @Test
    public void testNoParam(){
        RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/abnormal/notParam?name=1&a=1")
                .header( "Content-type","application/json")
                .exchange((request, response) -> {
                    System.out.println(response.getStatusCode());
                    Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
                    return response;
                });
    }

    @Test
    public void testParamConvertFail(){
        RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/abnormal/paramConvertFail?zonedDateTime=2023-03-08T10:15:30+08:00")
                .header( "Content-type","application/json")
                .exchange((request, response) -> {
                    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
                    return response;
                });
    }

    @Test
    public void testThrowException(){
        RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/abnormal/throwException")
                .header( "Content-type","application/json")
                .exchange((request, response) -> {
                    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
                    return response;
                });
    }

}
