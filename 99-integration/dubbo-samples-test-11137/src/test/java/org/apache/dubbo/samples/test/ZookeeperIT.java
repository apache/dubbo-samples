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

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.FrameworkModel;

import org.junit.Assert;
import org.junit.Test;

import static org.awaitility.Awaitility.await;

public class ZookeeperIT {
    private static final String nacosAddress = "zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181";
    private static final String providerNamePrefix = "provider" + System.currentTimeMillis();

    @Test
    public void test() {
        FrameworkModel frameworkModel1 = new FrameworkModel();
        FrameworkModel frameworkModel2 = new FrameworkModel();
        FrameworkModel frameworkModel3 = new FrameworkModel();
        FrameworkModel frameworkModel4 = new FrameworkModel();

        DemoServiceImpl demoService11 = new DemoServiceImpl();
        DemoServiceImpl demoService12 = new DemoServiceImpl();
        DemoServiceImpl demoService13 = new DemoServiceImpl();

        DemoService2Impl demoService21 = new DemoService2Impl();
        DemoService2Impl demoService22 = new DemoService2Impl();
        DemoService2Impl demoService23 = new DemoService2Impl();

        ServiceConfig<DemoService> serviceConfig11 = new ServiceConfig<>();
        serviceConfig11.setInterface(DemoService.class);
        serviceConfig11.setRef(demoService11);

        ServiceConfig<DemoService2> serviceConfig21 = new ServiceConfig<>();
        serviceConfig21.setInterface(DemoService2.class);
        serviceConfig21.setRef(demoService21);

        ApplicationConfig applicationConfig1 = new ApplicationConfig();
        applicationConfig1.setName(providerNamePrefix + "-1");
        applicationConfig1.setRegisterMode("instance");

        DubboBootstrap.getInstance(frameworkModel1.newApplication())
                .application(applicationConfig1)
                .protocol(new ProtocolConfig("dubbo", 20881))
                .service(serviceConfig11)
                .service(serviceConfig21)
                .registry(new RegistryConfig(nacosAddress))
                .start();

        ReferenceConfig<DemoService> referenceConfig1 = new ReferenceConfig<>();
        referenceConfig1.setInterface(DemoService.class);
        referenceConfig1.setScope("remote");
        ReferenceConfig<DemoService2> referenceConfig2 = new ReferenceConfig<>();
        referenceConfig2.setInterface(DemoService2.class);
        referenceConfig2.setScope("remote");

        DubboBootstrap.getInstance(frameworkModel2.newApplication())
                .application("consumer")
                .reference(referenceConfig1)
                .reference(referenceConfig2)
                .registry(new RegistryConfig(nacosAddress))
                .start();

        DemoService demoService1 = referenceConfig1.get();
        Assert.assertEquals("Hello world", demoService1.sayHello("world"));

        DemoService2 demoService2 = referenceConfig2.get();
        Assert.assertEquals("Hello world", demoService2.sayHello("world"));

        Assert.assertEquals(1, demoService11.getAtomicInteger().get());
        Assert.assertEquals(1, demoService21.getAtomicInteger().get());

        ServiceConfig<DemoService> serviceConfig12 = new ServiceConfig<>();
        serviceConfig12.setInterface(DemoService.class);
        serviceConfig12.setRef(demoService12);

        ServiceConfig<DemoService2> serviceConfig22 = new ServiceConfig<>();
        serviceConfig22.setInterface(DemoService2.class);
        serviceConfig22.setRef(demoService22);

        ApplicationConfig applicationConfig2 = new ApplicationConfig();
        applicationConfig2.setName(providerNamePrefix + "-2");
        applicationConfig2.setRegisterMode("instance");

        DubboBootstrap.getInstance(frameworkModel3.newApplication())
                .application(applicationConfig2)
                .protocol(new ProtocolConfig("dubbo", 20882))
                .service(serviceConfig12)
                .service(serviceConfig22)
                .registry(new RegistryConfig(nacosAddress))
                .start();

        await().until(()->{
            for (int i = 0; i < 10; i++) {
                demoService1.sayHello("world");
            }
            return demoService12.getAtomicInteger().get() > 0;
        });


        await().until(()->{
            for (int i = 0; i < 10; i++) {
                demoService2.sayHello("world");
            }
            return demoService22.getAtomicInteger().get() > 0;
        });


        ServiceConfig<DemoService> serviceConfig13 = new ServiceConfig<>();
        serviceConfig13.setInterface(DemoService.class);
        serviceConfig13.setRef(demoService13);

        ServiceConfig<DemoService2> serviceConfig23 = new ServiceConfig<>();
        serviceConfig23.setInterface(DemoService2.class);
        serviceConfig23.setRef(demoService23);

        ApplicationConfig applicationConfig3 = new ApplicationConfig();
        applicationConfig3.setName(providerNamePrefix + "-1");
        applicationConfig3.setRegisterMode("instance");

        DubboBootstrap.getInstance(frameworkModel1.newApplication())
                .application(applicationConfig3)
                .protocol(new ProtocolConfig("dubbo", 20883))
                .service(serviceConfig13)
                .service(serviceConfig23)
                .registry(new RegistryConfig(nacosAddress))
                .start();

        await().until(()->{
            for (int i = 0; i < 10; i++) {
                demoService1.sayHello("world");
            }
            return demoService13.getAtomicInteger().get() > 0;
        });


        await().until(()->{
            for (int i = 0; i < 10; i++) {
                demoService2.sayHello("world");
            }
            return demoService23.getAtomicInteger().get() > 0;
        });

        frameworkModel2.destroy();
        frameworkModel1.destroy();
        frameworkModel3.destroy();
    }
}
