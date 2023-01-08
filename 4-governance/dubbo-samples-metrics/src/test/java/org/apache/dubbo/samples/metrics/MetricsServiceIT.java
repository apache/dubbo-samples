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

package org.apache.dubbo.samples.metrics;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.monitor.MetricsService;
import org.apache.dubbo.samples.metrics.api.DemoService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/dubbo-demo-consumer.xml"})
public class MetricsServiceIT {
    private static String providerHost = System.getProperty("dubbo.address", "127.0.0.1");
    private static int providerPort = Integer.getInteger("dubbo.port", 20880);

    @Autowired
    private DemoService demoService;

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("result: " + demoService.sayHello("dubbo"));
        }

        ReferenceConfig<MetricsService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("metrics-demo-consumer"));
        reference.setUrl("dubbo://" + providerHost + ":" + providerPort + "/" + MetricsService.class.getName());
        reference.setInterface(MetricsService.class);
        MetricsService service = reference.get();
        String result = service.getMetricsByGroup("dubbo");
        JSONArray metrics = (JSONArray) JSON.parse(result);
        Assert.assertFalse(metrics.isEmpty());
        for (int i = 0; i < metrics.size(); i++) {
            String metric = (String) ((JSONObject) metrics.get(1)).get("metric");
            Assert.assertTrue(metric.startsWith("dubbo.provider") || metric.startsWith("threadPool"));
        }
    }
}
