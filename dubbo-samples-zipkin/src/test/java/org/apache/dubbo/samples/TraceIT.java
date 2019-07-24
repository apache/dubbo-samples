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

package org.apache.dubbo.samples;

import org.apache.dubbo.samples.api.GreetingService;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/client.xml"})
public class TraceIT {
    private static String zipkinAddress = "http://" + System.getProperty("zipkin.address", "localhost") + ":" +
            Integer.getInteger("zipkin.port", 9411);

    @Autowired
    private GreetingService greetingService;

    @Test
    public void testTrace() throws Exception {
        Assert.assertEquals("greeting, hello, world", greetingService.greeting("world"));

        Thread.sleep(2000);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(zipkinAddress + "/api/v2/services").addHeader(
                "accept", "application/json").build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            Gson gson = new Gson();
            List services = gson.fromJson(s, List.class);
            Assert.assertTrue(services.contains("client"));
            Assert.assertTrue(services.contains("greeting-service"));
            Assert.assertTrue(services.contains("hello-service"));
        }

        request = new Request.Builder().url(zipkinAddress + "/api/v2/traces?serviceName=client").addHeader(
                "accept", "application/json").build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            Gson gson = new Gson();
            Map[][] traces = gson.fromJson(s, Map[][].class);
            Assert.assertTrue(traces.length > 0);
            Map span = traces[0][0];
            Assert.assertNotNull(span.get("traceId"));
        }
    }
}
