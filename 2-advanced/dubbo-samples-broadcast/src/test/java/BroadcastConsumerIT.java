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

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ApplicationBuilder;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.apache.dubbo.samples.broadcast.api.DemoService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Consumer test side
 */
public class BroadcastConsumerIT {

    private DemoService broadcastService;
    private DemoService demoService;
    private DemoService demoService2;

    @Before
    public void setup() throws UnknownHostException {
        String ip = System.getProperty("zookeeper.address","localhost");
        String port = System.getProperty("zookeeper.port","2181");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(ip.trim());
        registryConfig.setPort(Integer.valueOf(port.trim()));
        registryConfig.setProtocol("zookeeper");

        ReferenceConfig<DemoService> broadcastReference = ReferenceBuilder.<DemoService>newBuilder()
                .interfaceClass(DemoService.class)
                .addRegistry(registryConfig)
                .cluster("broadcast")
                .version("*")
                .build();
        ReferenceConfig<DemoService> demoReference = ReferenceBuilder.<DemoService>newBuilder()
                .interfaceClass(DemoService.class)
                .addRegistry(registryConfig)
                .version("1.1.1")
                .build();
        ReferenceConfig<DemoService> demoReference2 = ReferenceBuilder.<DemoService>newBuilder()
                .interfaceClass(DemoService.class)
                .addRegistry(registryConfig)
                .version("1.1.2")
                .build();

        DubboBootstrap.getInstance()
                .application(ApplicationBuilder.newBuilder().qosPort(22223).name("broadcast").build())
                .reference(broadcastReference)
                .reference(demoReference)
                .reference(demoReference2)
                .start();

        broadcastService = broadcastReference.get();
        demoService = demoReference.get();
        demoService2 = demoReference2.get();
    }

    @Test
    public void testSayHello() {
        Assert.assertTrue(broadcastService.sayHello("world").contains("Hello"));
        Assert.assertTrue(demoService.isInvoke());
        Assert.assertTrue(demoService2.isInvoke());
    }


}
