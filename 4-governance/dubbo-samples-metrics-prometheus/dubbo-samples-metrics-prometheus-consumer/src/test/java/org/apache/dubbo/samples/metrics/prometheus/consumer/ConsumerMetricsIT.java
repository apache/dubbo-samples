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

package org.apache.dubbo.samples.metrics.prometheus.consumer;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ConsumerMetricsIT {

    private final String port = "20889";

    private final List<String> metricKeys = new ArrayList();

    @Before
    public void setUp() {
        add("dubbo_application_info_total");

        //config
        add("dubbo_configcenter_total");
        //register
        add("dubbo_registry_subscribe_num_total");
        add("dubbo_registry_register_requests_succeed_total");

        add("dubbo_register_service_rt_milliseconds_min");
        add("dubbo_registry_register_service_succeed_total");
        add("dubbo_register_service_rt_milliseconds_sum");
        add("dubbo_register_service_rt_milliseconds_max");
        add("dubbo_subscribe_rt_milliseconds_max");
        add("dubbo_subscribe_rt_milliseconds_min");
        add("dubbo_register_rt_milliseconds_last");
        add("dubbo_subscribe_rt_milliseconds_last");
        add("dubbo_registry_register_service_total");
        add("dubbo_registry_register_requests_total");
        add("dubbo_register_rt_milliseconds_avg");
        add("dubbo_registry_subscribe_num_succeed_total");
        add("dubbo_register_rt_milliseconds_max");
        add("dubbo_subscribe_rt_milliseconds_sum");
        add("dubbo_register_service_rt_milliseconds_avg");
        add("dubbo_register_service_rt_milliseconds_last");
        add("dubbo_subscribe_rt_milliseconds_avg");
        add("dubbo_register_rt_milliseconds_min");
        add("dubbo_register_rt_milliseconds_sum");

        
        //metadata

        //thread
        add("dubbo_thread_pool_thread_count");
        add("dubbo_thread_pool_largest_size");
        add("dubbo_thread_pool_active_size");
        add("dubbo_thread_pool_queue_size");
        add("dubbo_thread_pool_core_size");
        add("dubbo_thread_pool_max_size");
    }

    private void add(String dubboStoreProviderInterfaceRtMillisecondsMin) {
        metricKeys.add(dubboStoreProviderInterfaceRtMillisecondsMin);
    }

    @Test
    public void test() throws Exception {
        new EmbeddedZooKeeper(2181, false).start();

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-demo-consumer.xml");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://localhost:" + port + "/metrics");
            CloseableHttpResponse response = client.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));
            for (int i = 0; i < metricKeys.size(); i++) {
                Assert.assertTrue(text.contains(metricKeys.get(i)));
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        context.stop();
    }

}