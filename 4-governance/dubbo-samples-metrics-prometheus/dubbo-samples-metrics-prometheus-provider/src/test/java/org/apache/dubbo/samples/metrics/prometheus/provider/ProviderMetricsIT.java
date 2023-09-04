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

package org.apache.dubbo.samples.metrics.prometheus.provider;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ProviderMetricsIT {
    private static final Logger logger = LoggerFactory.getLogger(ProviderMetricsIT.class);

    private final String port = "20888";

    private final List<String> metricKeys = new ArrayList();

    @Before
    public void setUp() {
        //application
        add("dubbo_application_info_total");
        //provider
        add("dubbo_provider_requests_succeed_total");
        add("dubbo_provider_rt_milliseconds_p99");
        add("dubbo_provider_requests_failed_codec_total_aggregate");
        add("dubbo_provider_requests_business_failed_aggregate");
        add("dubbo_provider_requests_failed_aggregate");
        add("dubbo_provider_requests_timeout_failed_aggregate");
        add("dubbo_provider_requests_limit_aggregate");
        add("dubbo_provider_requests_failed_service_unavailable_total_aggregate");
        add("dubbo_provider_qps_total");
        add("dubbo_provider_rt_min_milliseconds_aggregate");
        add("dubbo_provider_rt_max_milliseconds_aggregate");
        add("dubbo_provider_rt_milliseconds_p90");
        add("dubbo_provider_rt_milliseconds_p50");
        add("dubbo_provider_rt_milliseconds_p95");
        add("dubbo_provider_requests_total");
        add("dubbo_provider_rt_avg_milliseconds_aggregate");
        add("dubbo_provider_requests_total_aggregate");
        add("dubbo_provider_requests_failed_total");
        add("dubbo_provider_requests_processing_total");
        add("dubbo_provider_requests_failed_total_aggregate");
        add("dubbo_provider_requests_failed_network_total_aggregate");
        add("dubbo_provider_requests_succeed_aggregate");
        add("dubbo_provider_requests_business_failed_total");

        //consumer
        add("dubbo_consumer_requests_failed_service_unavailable_total");

        //config
        add("dubbo_configcenter_total");
        //register
        add("dubbo_register_service_rt_milliseconds_sum");
        add("dubbo_registry_register_service_total");
        add("dubbo_registry_register_service_succeed_total");
        add("dubbo_register_service_rt_milliseconds_min");
        add("dubbo_registry_register_requests_succeed_total");
        add("dubbo_registry_register_requests_total");
        add("dubbo_register_rt_milliseconds_last");
        add("dubbo_register_rt_milliseconds_avg");
        add("dubbo_register_rt_milliseconds_min");
        add("dubbo_register_service_rt_milliseconds_last");
        add("dubbo_register_rt_milliseconds_sum");
        add("dubbo_register_service_rt_milliseconds_max");
        add("dubbo_register_rt_milliseconds_max");
        add("dubbo_register_service_rt_milliseconds_avg");

        add("dubbo_registry_notify_requests_total");
        add("dubbo_registry_subscribe_num_total");
        add("dubbo_registry_register_requests_failed_total");
        add("dubbo_registry_subscribe_num_succeed_total");
        add("dubbo_registry_subscribe_num_failed_total");


        add("dubbo_push_rt_milliseconds_avg");
        add("dubbo_push_rt_milliseconds_min");
        add("dubbo_push_rt_milliseconds_last");
        add("dubbo_push_rt_milliseconds_sum");
        add("dubbo_push_rt_milliseconds_max");

        //metadata
        add("dubbo_store_provider_interface_rt_milliseconds_min");
        add("dubbo_store_provider_interface_rt_milliseconds_max");
        add("dubbo_store_provider_interface_rt_milliseconds_avg");
        add("dubbo_store_provider_interface_rt_milliseconds_last");
        add("dubbo_store_provider_interface_rt_milliseconds_sum");
        add("dubbo_metadata_store_provider_succeed_total");
        add("dubbo_metadata_store_provider_total");

        add("dubbo_metadata_subscribe_num_failed_total");
        add("dubbo_metadata_push_num_total");
        add("dubbo_metadata_subscribe_num_total");
        add("dubbo_metadata_subscribe_num_succeed_total");
        add("dubbo_metadata_push_num_succeed_total");
        add("dubbo_metadata_push_num_failed_total");


        //thread
        add("dubbo_thread_pool_thread_count");
        add("dubbo_thread_pool_largest_size");
        add("dubbo_thread_pool_active_size");
        add("dubbo_thread_pool_queue_size");
        add("dubbo_thread_pool_core_size");
        add("dubbo_thread_pool_max_size");
        add("dubbo_thread_pool_reject_thread_count");
    }

    private void add(String dubboStoreProviderInterfaceRtMillisecondsMin) {
        metricKeys.add(dubboStoreProviderInterfaceRtMillisecondsMin);
    }

    @Test
    public void test() throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-demo-provider.xml");
        context.start();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://localhost:" + port + "/metrics");
            CloseableHttpResponse response = client.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));
            //O(size)
            for (int i = 0; i < metricKeys.size(); i++) {
                String metricKey = metricKeys.get(i);
                try {
                    Assert.assertTrue(text.contains(metricKey));
                } catch (Throwable e) {
                    logger.error("metric key:{} don't exists", metricKey);
                    throw new RuntimeException(e);
                }
            }
        } catch (Throwable e) {
            Assert.fail(e.getMessage());
        }
        context.stop();

    }

}