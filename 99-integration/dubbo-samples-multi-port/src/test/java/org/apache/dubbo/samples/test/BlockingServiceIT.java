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

package org.apache.dubbo.samples.test;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.samples.api.BlockingService;
import org.apache.dubbo.samples.api.ManagementService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/dubbo-demo-consumer.xml")
public class BlockingServiceIT {
    @Autowired
    @Qualifier("blockingServiceA")
    private BlockingService blockingServiceA;
    @Autowired
    @Qualifier("blockingServiceB")
    private BlockingService blockingServiceB;
    @Autowired
    @Qualifier("blockingServiceC")
    private BlockingService blockingServiceC;
    @Autowired
    @Qualifier("blockingServiceD")
    private BlockingService blockingServiceD;
    @Autowired
    @Qualifier("blockingServiceE")
    private BlockingService blockingServiceE;

    @Autowired
    private ManagementService managementService;

    @Test
    public void test() {
        Map<Integer, String> portToGroup = new HashMap<>();
        portToGroup.put(30010, "dubbo-h");
        portToGroup.put(30011, "dubbo-m");
        portToGroup.put(30012, "dubbo-l");
        portToGroup.put(30013, "dubbo-danger");
        portToGroup.put(30014, "dubbo-openapi");

        for (Integer port : portToGroup.keySet()) {
            for (String group : portToGroup.values()) {
                ReferenceConfig<BlockingService> reference = new ReferenceConfig<>();
                reference.setGroup(group);
                reference.setInterface(BlockingService.class);
                String host = System.getProperty("provider.address", "127.0.0.1");
                reference.setUrl("dubbo://" + host + ":" + port);
                BlockingService blockingService = reference.get();
                if (portToGroup.get(port).equals(group)) {
                    String type = blockingService.type();
                    Assert.assertEquals(group, type);
                } else {
                    try {
                        blockingService.type();
                        Assert.fail();
                    } catch (Exception e) {
                        Assert.assertTrue(e.getMessage().contains("Not found exported service"));
                    }
                }
                reference.destroy();
            }
        }

        Assert.assertEquals("dubbo-h", blockingServiceA.type());
        Assert.assertEquals("dubbo-m", blockingServiceB.type());
        Assert.assertEquals("dubbo-l", blockingServiceC.type());
        Assert.assertEquals("dubbo-danger", blockingServiceD.type());
        Assert.assertEquals("dubbo-openapi", blockingServiceE.type());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> blockingServiceA.block()).start();
        }
        for (int i = 0; i < 11; i++) {
            new Thread(() -> blockingServiceB.block()).start();
        }
        for (int i = 0; i < 12; i++) {
            new Thread(() -> blockingServiceC.block()).start();
        }
        for (int i = 0; i < 13; i++) {
            new Thread(() -> blockingServiceD.block()).start();
        }
        for (int i = 0; i < 14; i++) {
            new Thread(() -> blockingServiceE.block()).start();
        }

        await().untilAsserted(() -> {
            Map<String, AtomicInteger> counters = managementService.getCounters();
            Assert.assertEquals(10, counters.get("dubbo-h").get());
            Assert.assertEquals(11, counters.get("dubbo-m").get());
            Assert.assertEquals(12, counters.get("dubbo-l").get());
            Assert.assertEquals(13, counters.get("dubbo-danger").get());
            Assert.assertEquals(14, counters.get("dubbo-openapi").get());
        });

        Map<Integer, Integer> executorsSize = managementService.getExecutorsSize();
        Assert.assertEquals(10, executorsSize.get(30010).intValue());
        Assert.assertEquals(11, executorsSize.get(30011).intValue());
        Assert.assertEquals(12, executorsSize.get(30012).intValue());
        Assert.assertEquals(13, executorsSize.get(30013).intValue());
        Assert.assertEquals(14, executorsSize.get(30014).intValue());

        try {
            blockingServiceA.type();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Thread pool is EXHAUSTED"));
        }
        try {
            blockingServiceB.type();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Thread pool is EXHAUSTED"));
        }
        try {
            blockingServiceC.type();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Thread pool is EXHAUSTED"));
        }
        try {
            blockingServiceD.type();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Thread pool is EXHAUSTED"));
        }
        try {
            blockingServiceE.type();
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Thread pool is EXHAUSTED"));
        }
    }

}
