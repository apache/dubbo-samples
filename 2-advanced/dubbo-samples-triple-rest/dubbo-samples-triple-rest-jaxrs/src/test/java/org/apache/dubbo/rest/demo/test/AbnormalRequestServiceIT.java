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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@SuppressWarnings("resource")
public class AbnormalRequestServiceIT extends BaseTest {

    @Test
    public void testNotFound() {
        RestClient.create()
                .get()
                .uri(toUri("/abnormal/not"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                    return response;
                });
    }

    @Test
    public void testNoParam() {
        RestClient.create()
                .get()
                .uri(toUri("/abnormal/notParam?name=1&a=1"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    System.out.println(response.getStatusCode());
                    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                    return response;
                });
    }

    @Test
    public void testParamConvertFail() {
        RestClient.create()
                .get()
                .uri(toUri("/abnormal/paramConvertFail?zonedDateTime=2023-03-08T10:15:30+08:00"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                    return response;
                });
    }

    @Test
    public void testThrowException() {
        RestClient.create()
                .get()
                .uri(toUri("/abnormal/throwException"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                    return response;
                });
    }

}
