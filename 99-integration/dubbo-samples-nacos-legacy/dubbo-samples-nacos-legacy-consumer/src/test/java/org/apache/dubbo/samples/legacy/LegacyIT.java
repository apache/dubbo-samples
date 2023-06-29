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
package org.apache.dubbo.samples.legacy;

import org.apache.dubbo.common.Version;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.legacy.api.DemoService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class LegacyIT {
    @After
    public void teardown() {
        FrameworkModel.destroyAll();
        DubboBootstrap.reset();
    }

    @Test
    public void testFor3_2() {
        if (!Version.getVersion().startsWith("3.2")) {
            return;
        }

        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setLoadbalance("roundrobin");

        DubboBootstrap bootstrap = DubboBootstrap.newInstance()
                .application("demo-client")
                .registry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos"))
                .reference(referenceConfig)
                .start();

        DemoService demoService = referenceConfig.get();
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .anyMatch(s -> s.startsWith("hello from 2")));
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .peek(System.out::println)
                .anyMatch(s -> s.startsWith("hello from 3")));

        bootstrap.stop();
    }

    @Test
    public void testFor3_3() {
        if (!Version.getVersion().startsWith("3.3")) {
            return;
        }

        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setLoadbalance("roundrobin");

        DubboBootstrap bootstrap = DubboBootstrap.newInstance()
                .application("demo-client")
                .registry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos"))
                .reference(referenceConfig)
                .start();

        DemoService demoService = referenceConfig.get();
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .peek(System.out::println)
                .noneMatch(s -> s.startsWith("hello from 2")));

        bootstrap.stop();
    }

    @Test
    public void testEnable() {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setLoadbalance("roundrobin");

        DubboBootstrap bootstrap = DubboBootstrap.newInstance()
                .application("demo-client")
                .registry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos&nacos.subscribe.legacy-name=true"))
                .reference(referenceConfig)
                .start();

        DemoService demoService = referenceConfig.get();
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .peek(System.out::println)
                .anyMatch(s -> s.startsWith("hello from 2")));
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .peek(System.out::println)
                .anyMatch(s -> s.startsWith("hello from 3")));

        bootstrap.stop();
    }

    @Test
    public void testDisable() {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setLoadbalance("roundrobin");

        DubboBootstrap bootstrap = DubboBootstrap.newInstance()
                .application("demo-client")
                .registry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos&nacos.subscribe.legacy-name=false"))
                .reference(referenceConfig)
                .start();

        DemoService demoService = referenceConfig.get();
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .peek(System.out::println)
                .allMatch(s -> s.startsWith("hello from 3")));

        bootstrap.stop();
    }
}
