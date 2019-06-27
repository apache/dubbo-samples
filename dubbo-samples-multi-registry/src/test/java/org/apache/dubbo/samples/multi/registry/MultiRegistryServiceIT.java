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

import org.apache.dubbo.samples.multi.registry.api.DemoService;
import org.apache.dubbo.samples.multi.registry.api.HelloService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/multi-registry-consumer.xml"})
public class MultiRegistryServiceIT {
    @Autowired
    @Qualifier("demoServiceFormDefault")
    private DemoService defaultDemoService;

    @Autowired
    @Qualifier("demoServiceFormBeijing")
    private DemoService beijingDemoService;

    @Autowired
    @Qualifier("helloServiceFormBeijing")
    private HelloService beijingHelloService;

    @Autowired
    @Qualifier("helloServiceFormShanghai")
    private HelloService shanghaiHelloService;

    @Test
    public void testDefaultDemoService() throws Exception {
        Assert.assertEquals("get: demo", defaultDemoService.get("demo"));
    }

    @Test
    public void testBeijingDemoService() throws Exception {
        Assert.assertEquals("get: beijing", beijingDemoService.get("beijing"));
    }

    @Test
    public void testBeijingHelloService() throws Exception {
        Assert.assertEquals("sayHello: beijing", beijingHelloService.sayHello("beijing"));
    }

    @Test
    public void testShanghaiHelloService() throws Exception {
        Assert.assertEquals("sayHello: shanghai", shanghaiHelloService.sayHello("shanghai"));
    }

    @Test
    public void verifyProvidersFromBeijingRegistry() throws Exception {
        List<String> demoServiceProviders = ZKTools.getProviders(DemoService.class, 2181);
        Assert.assertEquals(1, demoServiceProviders.size());
        List<String> helloServiceProviders = ZKTools.getProviders(HelloService.class, 2181);
        Assert.assertEquals(1, helloServiceProviders.size());
    }

    @Test
    public void verifyProvidersFromShanghaiRegistry() throws Exception {
        List<String> demoServiceProviders = ZKTools.getProviders(DemoService.class, 2182);
        Assert.assertEquals(1, demoServiceProviders.size());
        List<String> helloServiceProviders = ZKTools.getProviders(HelloService.class, 2182);
        Assert.assertEquals(1, helloServiceProviders.size());
    }
}
