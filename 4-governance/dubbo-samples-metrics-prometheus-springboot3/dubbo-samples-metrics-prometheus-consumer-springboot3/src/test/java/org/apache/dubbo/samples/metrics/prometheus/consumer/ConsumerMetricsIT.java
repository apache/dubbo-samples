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

import org.apache.dubbo.samples.metrics.prometheus.util.PrometheusMetricAssert;
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


public class ConsumerMetricsIT {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerMetricsIT.class);

    private final String port = "20889";

    private final List<MetricExpectation> metricExpectations = new ArrayList<>();

    @Before
    public void setUp() {
        addCounter("dubbo.application.info.total");

        //config
        addGauge("dubbo.configcenter.total");

        addGauge("dubbo.provider.rt.milliseconds.p99");
        addGauge("dubbo.provider.requests.total.aggregate");
        addGauge("dubbo.provider.rt.milliseconds.p90");
        addGauge("dubbo.provider.rt.max.milliseconds.aggregate");
        addCounter("dubbo.provider.requests.succeed.total");
        addGauge("dubbo.provider.requests.business.failed.aggregate");
        addCounter("dubbo.provider.requests.business.failed.total");
        addCounter("dubbo.provider.requests.total");
        addGauge("dubbo.provider.requests.failed.service.unavailable.total.aggregate");
        addGauge("dubbo.provider.requests.limit.aggregate");
        addGauge("dubbo.provider.qps.total");
        addGauge("dubbo.provider.rt.milliseconds.p50");
        addGauge("dubbo.provider.requests.processing.total");
        addCounter("dubbo.provider.requests.failed.total");
        addGauge("dubbo.provider.requests.succeed.aggregate");
        addGauge("dubbo.provider.rt.milliseconds.p95");
        addGauge("dubbo.provider.requests.failed.aggregate");
        addGauge("dubbo.provider.rt.avg.milliseconds.aggregate");
        addGauge("dubbo.provider.requests.failed.total.aggregate");
        addGauge("dubbo.provider.requests.failed.network.total.aggregate");
        addGauge("dubbo.provider.rt.min.milliseconds.aggregate");
        addGauge("dubbo.provider.requests.failed.codec.total.aggregate");
        addGauge("dubbo.provider.requests.timeout.failed.aggregate");

        //consumer
        addCounter("dubbo.consumer.requests.failed.service.unavailable.total");

        //register
        addGauge("dubbo.registry.subscribe.num.total");
        addGauge("dubbo.registry.register.requests.succeed.total");
        addGauge("dubbo.register.rt.milliseconds.last");
        addGauge("dubbo.registry.register.requests.total");
        addGauge("dubbo.register.rt.milliseconds.avg");
        addGauge("dubbo.registry.subscribe.num.succeed.total");
        addGauge("dubbo.register.rt.milliseconds.max");
        addGauge("dubbo.register.rt.milliseconds.min");
        addGauge("dubbo.register.rt.milliseconds.sum");
        addGauge("dubbo.registry.notify.requests.total");
        addGauge("dubbo.registry.register.requests.failed.total");
        addGauge("dubbo.registry.subscribe.num.failed.total");
        addGauge("dubbo.register.rt.milliseconds.max");
        addGauge("dubbo.registry.register.requests.succeed.total");

        //metadata
        addGauge("dubbo.metadata.subscribe.num.failed.total");
        addGauge("dubbo.metadata.push.num.failed.total");
        addGauge("dubbo.metadata.subscribe.num.total");
        addGauge("dubbo.metadata.subscribe.num.succeed.total");
        addGauge("dubbo.metadata.push.num.succeed.total");
        addGauge("dubbo.metadata.push.num.total");

        //thread
        addGauge("dubbo.thread.pool.thread.count");
        addGauge("dubbo.thread.pool.largest.size");
        addGauge("dubbo.thread.pool.active.size");
        addGauge("dubbo.thread.pool.queue.size");
        addGauge("dubbo.thread.pool.core.size");
        addGauge("dubbo.thread.pool.max.size");
    }

    // Use explicit kind only when it changes cross-registry compatible names.
    // Example: `dubbo.application.info.total` resolves to `dubbo_application_total` on new clients.
    private void addCounter(String rawName) {
        metricExpectations.add(new MetricExpectation(rawName, PrometheusMetricAssert.MetricKind.COUNTER));
    }

    // Example: `...qps.total` is exposed without `_total` on new clients when treated as a gauge.
    private void addGauge(String rawName) {
        metricExpectations.add(new MetricExpectation(rawName, PrometheusMetricAssert.MetricKind.GAUGE));
    }

    @Test
    public void test() throws Exception {
        new EmbeddedZooKeeper(2181, false).start();

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-demo-consumer.xml");

        List<MetricExpectation> notExistedList = new ArrayList<>();
        HttpGet request = new HttpGet("http://localhost:" + port + "/metrics");
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // retry 3 times as all metrics data are not collected immediately.
            for (int retryTime = 0; retryTime < 3; retryTime++) {
                notExistedList.clear();
                CloseableHttpResponse response = client.execute(request);
                InputStream inputStream = response.getEntity().getContent();
                String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                        .collect(Collectors.joining("\n"));
                for (MetricExpectation expectation : metricExpectations) {
                    try {
                        PrometheusMetricAssert.assertDubboMetricExposed(text, expectation.rawName, expectation.kind);
                    } catch (AssertionError ignored) {
                        notExistedList.add(expectation);
                    }
                }
                if (notExistedList.isEmpty()) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        context.stop();
        for (MetricExpectation metric : notExistedList) {
            logger.error("metric rawName:{} with kind:{} doesn't exist", metric.rawName, metric.kind);
        }
        Assert.assertTrue(notExistedList.isEmpty());
    }

    private static final class MetricExpectation {
        private final String rawName;
        private final PrometheusMetricAssert.MetricKind kind;

        private MetricExpectation(String rawName, PrometheusMetricAssert.MetricKind kind) {
            this.rawName = rawName;
            this.kind = kind;
        }
    }
}
