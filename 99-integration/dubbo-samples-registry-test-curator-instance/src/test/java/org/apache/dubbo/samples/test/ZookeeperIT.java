/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.dubbo.samples.test;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.api.ControlService;
import org.apache.dubbo.samples.api.DemoService1;
import org.apache.dubbo.samples.api.DemoService2;
import org.apache.dubbo.samples.api.DemoService3;

import org.awaitility.Awaitility;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ZookeeperIT {
    @Before
    public void setup() {
        DubboBootstrap.reset();
        FrameworkModel.destroyAll();
    }

    @After
    public void teardown() {
        DubboBootstrap.reset();
        FrameworkModel.destroyAll();
    }

    @Test
    public void test() {
        String zookeeperAddress = "zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181";

        ReferenceConfig<DemoService1> referenceConfig1 = new ReferenceConfig<>();
        referenceConfig1.setInterface(DemoService1.class);

        ReferenceConfig<DemoService2> referenceConfig2 = new ReferenceConfig<>();
        referenceConfig2.setInterface(DemoService2.class);

        ReferenceConfig<DemoService3> referenceConfig3 = new ReferenceConfig<>();
        referenceConfig3.setInterface(DemoService3.class);

        RegistryConfig registryConfig = new RegistryConfig(zookeeperAddress);

        ReferenceConfig<ControlService> referenceConfigA = new ReferenceConfig<>();
        referenceConfigA.setInterface(ControlService.class);
        referenceConfigA.setVersion("App1");

        ReferenceConfig<ControlService> referenceConfigB = new ReferenceConfig<>();
        referenceConfigB.setInterface(ControlService.class);
        referenceConfigB.setVersion("App2");

        ReferenceConfig<ControlService> referenceConfigC = new ReferenceConfig<>();
        referenceConfigC.setInterface(ControlService.class);
        referenceConfigC.setVersion("App3");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application("Consumer")
                .registry(registryConfig)
                .reference(referenceConfig1)
                .reference(referenceConfig2)
                .reference(referenceConfig3)
                .reference(referenceConfigA)
                .reference(referenceConfigB)
                .reference(referenceConfigC)
                .start();

        DemoService1 demoService1 = referenceConfig1.get();
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals("App1", demoService1.getRemote());
        }

        DemoService2 demoService2 = referenceConfig2.get();
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals("App2", demoService2.getRemote());
        }

        DemoService3 demoService3 = referenceConfig3.get();
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals("App3", demoService3.getRemote());
        }

        ControlService controlService1 = referenceConfigA.get();
        ControlService controlService2 = referenceConfigB.get();
        ControlService controlService3 = referenceConfigC.get();

        controlService1.exportService("DemoService2");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App1".equals(demoService2.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService1.exportService("DemoService3");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App1".equals(demoService2.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService2.exportService("DemoService1");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App2".equals(demoService1.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService2.exportService("DemoService3");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App2".equals(demoService3.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService3.exportService("DemoService1");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App3".equals(demoService1.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService3.exportService("DemoService2");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App3".equals(demoService2.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        Set<String> result = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            result.add(demoService1.getRemote());
        }
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains("App1"));
        Assert.assertTrue(result.contains("App2"));
        Assert.assertTrue(result.contains("App3"));

        result = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            result.add(demoService2.getRemote());
        }
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains("App1"));
        Assert.assertTrue(result.contains("App2"));
        Assert.assertTrue(result.contains("App3"));

        result = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            result.add(demoService3.getRemote());
        }
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains("App1"));
        Assert.assertTrue(result.contains("App2"));
        Assert.assertTrue(result.contains("App3"));

        controlService1.unExportService("DemoService2");
        controlService1.unExportService("DemoService3");

        controlService2.unExportService("DemoService1");
        controlService2.unExportService("DemoService3");

        controlService3.unExportService("DemoService1");
        controlService3.unExportService("DemoService2");

        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    for (int i = 0; i < 100; i++) {
                        Assert.assertEquals("App1", demoService1.getRemote());
                    }
                });

        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    for (int i = 0; i < 100; i++) {
                        Assert.assertEquals("App2", demoService2.getRemote());
                    }
                });

        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    for (int i = 0; i < 100; i++) {
                        Assert.assertEquals("App3", demoService3.getRemote());
                    }
                });

        bootstrap.stop();
    }

    @Test
    public void testIsolated() {
        String zookeeperAddress = "zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181?group=isolated";

        ReferenceConfig<DemoService1> referenceConfig1 = new ReferenceConfig<>();
        referenceConfig1.setInterface(DemoService1.class);

        ReferenceConfig<DemoService2> referenceConfig2 = new ReferenceConfig<>();
        referenceConfig2.setInterface(DemoService2.class);

        ReferenceConfig<DemoService3> referenceConfig3 = new ReferenceConfig<>();
        referenceConfig3.setInterface(DemoService3.class);

        RegistryConfig registryConfig = new RegistryConfig(zookeeperAddress);

        ReferenceConfig<ControlService> referenceConfigA = new ReferenceConfig<>();
        referenceConfigA.setInterface(ControlService.class);
        referenceConfigA.setVersion("App4");

        ReferenceConfig<ControlService> referenceConfigB = new ReferenceConfig<>();
        referenceConfigB.setInterface(ControlService.class);
        referenceConfigB.setVersion("App5");

        ReferenceConfig<ControlService> referenceConfigC = new ReferenceConfig<>();
        referenceConfigC.setInterface(ControlService.class);
        referenceConfigC.setVersion("App6");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application("Consumer")
                .registry(registryConfig)
                .reference(referenceConfig1)
                .reference(referenceConfig2)
                .reference(referenceConfig3)
                .reference(referenceConfigA)
                .reference(referenceConfigB)
                .reference(referenceConfigC)
                .start();

        DemoService1 demoService1 = referenceConfig1.get();
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals("App4", demoService1.getRemote());
        }

        DemoService2 demoService2 = referenceConfig2.get();
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals("App5", demoService2.getRemote());
        }

        DemoService3 demoService3 = referenceConfig3.get();
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals("App6", demoService3.getRemote());
        }

        ControlService controlService1 = referenceConfigA.get();
        ControlService controlService2 = referenceConfigB.get();
        ControlService controlService3 = referenceConfigC.get();

        controlService1.exportService("DemoService2");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App4".equals(demoService2.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService1.exportService("DemoService3");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App4".equals(demoService2.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService2.exportService("DemoService1");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App5".equals(demoService1.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService2.exportService("DemoService3");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App5".equals(demoService3.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService3.exportService("DemoService1");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App6".equals(demoService1.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        controlService3.exportService("DemoService2");
        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .until(() -> {
                    for (int i = 0; i < 10; i++) {
                        if ("App6".equals(demoService2.getRemote())) {
                            return true;
                        }
                    }
                    return false;
                });

        Set<String> result = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            result.add(demoService1.getRemote());
        }
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains("App4"));
        Assert.assertTrue(result.contains("App5"));
        Assert.assertTrue(result.contains("App6"));

        result = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            result.add(demoService2.getRemote());
        }
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains("App4"));
        Assert.assertTrue(result.contains("App5"));
        Assert.assertTrue(result.contains("App6"));

        result = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            result.add(demoService3.getRemote());
        }
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains("App4"));
        Assert.assertTrue(result.contains("App5"));
        Assert.assertTrue(result.contains("App6"));

        controlService1.unExportService("DemoService2");
        controlService1.unExportService("DemoService3");

        controlService2.unExportService("DemoService1");
        controlService2.unExportService("DemoService3");

        controlService3.unExportService("DemoService1");
        controlService3.unExportService("DemoService2");

        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    for (int i = 0; i < 100; i++) {
                        Assert.assertEquals("App4", demoService1.getRemote());
                    }
                });

        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    for (int i = 0; i < 100; i++) {
                        Assert.assertEquals("App5", demoService2.getRemote());
                    }
                });

        Awaitility.await().atMost(60, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    for (int i = 0; i < 100; i++) {
                        Assert.assertEquals("App6", demoService3.getRemote());
                    }
                });

        bootstrap.stop();
    }
}
