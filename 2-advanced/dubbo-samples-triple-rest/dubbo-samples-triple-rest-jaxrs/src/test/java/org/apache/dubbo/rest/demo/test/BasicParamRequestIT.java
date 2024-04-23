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
import org.apache.dubbo.rest.demo.routine.BasicParamRequestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BasicParamRequestIT {
    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");

    @DubboReference
    private BasicParamRequestService basicParamRequestService;

    @Test
    public void test() {
        int result1 = basicParamRequestService.primitiveInt(1, 1);
        Assert.assertEquals(2, result1);

        byte result2 = basicParamRequestService.primitiveByte((byte) 1, (byte) 1);
        Assert.assertEquals((byte) 2, result2);

        long result3 = basicParamRequestService.primitiveLong(1L, 1L);
        Assert.assertEquals(2L, result3);

        double result4 = basicParamRequestService.primitiveDouble(1.1, 1.2);
        Assert.assertEquals(2.3,result4,0.00001);

        String result5 = basicParamRequestService.primitiveString("Hello ", "world");
        Assert.assertEquals("Hello world", result5);

    }

    @Test
    public void testInt() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Integer> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveByte?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Integer.class);
        Assert.assertEquals(Integer.valueOf(2), result.getBody());
    }

    @Test
    public void testByte() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Byte> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitive?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Byte.class);
        Assert.assertEquals(Byte.valueOf((byte) 2), result.getBody());
    }

    @Test
    public void testLong() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Long> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveLong?a={a}&b={b}", 1, 1)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Long.class);
        Assert.assertEquals(Long.valueOf(2), result.getBody());
    }

    @Test
    public void testDouble() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<Double> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveDouble?a={a}&b={b}", 1.1, 1.2)
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(Double.class);
        Assert.assertEquals(Double.valueOf(2.3), result.getBody());
    }

    @Test
    public void testString() {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/primitiveString?a={a}&b={b}", "Hello ", "world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world", result.getBody());
    }

}
