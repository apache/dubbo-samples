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

package org.apache.dubbo.samples.monitor;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.monitor.MonitorService;
import org.apache.dubbo.samples.monitor.api.DemoService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/dubbo-demo-consumer.xml"})
public class MonitorServiceIT {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    @Autowired
    private DemoService demoService;

    @BeforeClass
    public static void setUp() throws Exception {
        ServiceConfig<MonitorService> service = new ServiceConfig<>();
        // FIXME: has to set application name to "demo-consumer"
        service.setApplication(new ApplicationConfig("demo-consumer"));
        service.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        service.setInterface(MonitorService.class);
        service.setRef(new MonitorServiceImpl());
        service.export();
    }

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 10; i++) {
            demoService.sayHello("world");
            Thread.sleep(50);
        }

        ReferenceConfig<MonitorService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("demo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(MonitorService.class);
        MonitorService service = reference.get();
        List<URL> stats = service.lookup(null);

        boolean countProvider = false;
        boolean countConsumer = false;
        for (URL stat : stats) {
            Assert.assertEquals("count", stat.getProtocol());
            Assert.assertEquals("org.apache.dubbo.samples.monitor.api.DemoService/sayHello", stat.getPath());
            if (stat.getParameter("application").equals("demo-provider")) {
                countProvider = true;
            }
            if (stat.getParameter("application").equals("demo-consumer")) {
                countConsumer = true;
            }
            System.out.println(stat);
        }
        Assert.assertTrue(countConsumer && countProvider);
    }
}
