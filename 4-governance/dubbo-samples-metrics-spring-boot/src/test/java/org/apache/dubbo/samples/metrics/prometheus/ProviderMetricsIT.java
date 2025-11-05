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

package org.apache.dubbo.samples.metrics.prometheus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.awaitility.Awaitility;
import org.junit.Assert;
import org.junit.Test;

public class ProviderMetricsIT {

    @Test
    public void test() throws Exception {
        Awaitility.await()
                .atMost(3, TimeUnit.MINUTES)
                .untilAsserted(()->{
                    try (CloseableHttpClient client = HttpClients.createDefault()) {
                        HttpGet request = new HttpGet("http://" + System.getProperty("provider", "localhost") + ":18081/management/prometheus");
                        CloseableHttpResponse response = client.execute(request);
                        InputStream inputStream = response.getEntity().getContent();
                        String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
                        Assert.assertTrue(text.contains("jvm_gc_memory_promoted_bytes_total"));
                        Assert.assertTrue(text.contains("dubbo_application_info_total"));
                    } catch (Exception e) {
                        Assert.fail(e.getMessage());
                    }
                });
    }
}
