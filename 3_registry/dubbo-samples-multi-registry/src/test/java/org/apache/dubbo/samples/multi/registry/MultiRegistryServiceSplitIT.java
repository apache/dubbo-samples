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

package org.apache.dubbo.samples.multi.registry;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.multi.registry.api.DemoService;
import org.apache.dubbo.samples.multi.registry.api.HelloService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MultiRegistryServiceSplitIT {

    @Before
    public void setUp() {
        try {
            DubboBootstrap.reset();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConsumer1() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/multi-registry-consumer1.xml");
        DemoService demoService = applicationContext.getBean(DemoService.class);
        HelloService helloService = applicationContext.getBean(HelloService.class);
        Assert.assertEquals("get: demo", demoService.get("demo"));
        Assert.assertEquals("sayHello: beijing", helloService.sayHello("beijing"));
    }

    @Test
    public void testConsumer2() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/multi-registry-consumer2.xml");
        DemoService demoService = applicationContext.getBean(DemoService.class);
        HelloService helloService = applicationContext.getBean(HelloService.class);
        Assert.assertEquals("get: demo", demoService.get("demo"));
        Assert.assertEquals("sayHello: beijing", helloService.sayHello("beijing"));
    }

    @Test
    public void testConsumer3() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/multi-registry-consumer3.xml");
        DemoService demoService = applicationContext.getBean(DemoService.class);
        HelloService helloService = applicationContext.getBean(HelloService.class);
        Assert.assertEquals("get: demo", demoService.get("demo"));
        Assert.assertEquals("sayHello: beijing", helloService.sayHello("beijing"));
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
