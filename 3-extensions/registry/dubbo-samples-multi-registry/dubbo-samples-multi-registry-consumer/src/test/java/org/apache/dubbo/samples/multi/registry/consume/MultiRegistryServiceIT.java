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
package org.apache.dubbo.samples.multi.registry.consume;

import java.util.List;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.multi.registry.api.DemoService;
import org.apache.dubbo.samples.multi.registry.api.HelloService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * multi registry service test
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MultiRegistryConsumer.class)
public class MultiRegistryServiceIT {
    @DubboReference(interfaceClass = DemoService.class,registry = "beijingRegistry")
    private DemoService demoServiceBeijing;
    @DubboReference(interfaceClass = HelloService.class,registry = "shanghaiRegistry")
    private HelloService helloServiceShanghai;
    @DubboReference(interfaceClass = HelloService.class,registry = "beijingRegistry")
    private HelloService helloServiceBeijing;
    @DubboReference(interfaceClass = DemoService.class,registry = "shanghaiRegistry")
    private DemoService demoServiceShanghai;
    @Test
    public void testConsumer1() {
        Assert.assertEquals("get: demo", demoServiceBeijing.get("demo"));
        Assert.assertEquals("sayHello: beijing", helloServiceShanghai.sayHello("beijing"));
    }
    @Test
    public void testConsumer2() {
        Assert.assertEquals("get: demo", demoServiceBeijing.get("demo"));
        Assert.assertEquals("sayHello: beijing", helloServiceBeijing.sayHello("beijing"));
    }

    @Test
    public void testConsumer3() {
        Assert.assertEquals("get: demo", demoServiceShanghai.get("demo"));
        Assert.assertEquals("sayHello: beijing", helloServiceShanghai.sayHello("beijing"));
    }

    @Test
    public void verifyProvidersFromBeijingRegistry() throws Exception {
        List<String> demoServiceProviders = ZKTools.getProviders(DemoService.class, "zookeeper.address.1", 2181);
        Assert.assertEquals(1, demoServiceProviders.size());
        List<String> helloServiceProviders = ZKTools.getProviders(HelloService.class, "zookeeper.address.1", 2181);
        Assert.assertEquals(1, helloServiceProviders.size());
    }

    @Test
    public void verifyProvidersFromShanghaiRegistry() throws Exception {
        List<String> demoServiceProviders = ZKTools.getProviders(DemoService.class, "zookeeper.address.2", 2181);
        Assert.assertEquals(1, demoServiceProviders.size());
        List<String> helloServiceProviders = ZKTools.getProviders(HelloService.class, "zookeeper.address.2", 2181);
        Assert.assertEquals(1, helloServiceProviders.size());
    }
}
