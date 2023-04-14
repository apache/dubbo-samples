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
package org.apache.dubbo.samples.api;

import org.apache.dubbo.common.threadlocal.NamedInternalThreadFactory;
import org.apache.dubbo.common.threadpool.manager.ExecutorRepository;
import org.apache.dubbo.common.threadpool.manager.IsolationExecutorRepository;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.support.DemoService;
import org.apache.dubbo.samples.support.DemoServiceImpl;
import org.apache.dubbo.samples.support.HelloService;
import org.apache.dubbo.samples.support.HelloServiceImpl;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.apache.dubbo.common.constants.CommonConstants.EXECUTOR_MANAGEMENT_MODE_ISOLATION;

public class ApiIsolationIT {

    private static final String version1 = "1.0";
    private static final String version2 = "2.0";
    private static final String version3 = "3.0";

    @Test
    public void test() {
        String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://" + zookeeperHost + ":2181");

        DubboBootstrap providerBootstrap = null;
        DubboBootstrap consumerBootstrap1 = null;
        DubboBootstrap consumerBootstrap2 = null;

        try {

            // provider app
            providerBootstrap = DubboBootstrap.newInstance();

            ServiceConfig serviceConfig1 = new ServiceConfig();
            serviceConfig1.setInterface(DemoService.class);
            serviceConfig1.setRef(new DemoServiceImpl());
            serviceConfig1.setVersion(version1);
            // set executor1 for serviceConfig1, max threads is 10
            NamedThreadFactory threadFactory1 = new NamedThreadFactory("DemoServiceExecutor");
            ExecutorService executor1 = Executors.newFixedThreadPool(10, threadFactory1);
            serviceConfig1.setExecutor(executor1);

            ServiceConfig serviceConfig2 = new ServiceConfig();
            serviceConfig2.setInterface(HelloService.class);
            serviceConfig2.setRef(new HelloServiceImpl());
            serviceConfig2.setVersion(version2);
            // set executor2 for serviceConfig2, max threads is 100
            NamedThreadFactory threadFactory2 = new NamedThreadFactory("HelloServiceExecutor");
            ExecutorService executor2 = Executors.newFixedThreadPool(100, threadFactory2);
            serviceConfig2.setExecutor(executor2);

            ServiceConfig serviceConfig3 = new ServiceConfig();
            serviceConfig3.setInterface(HelloService.class);
            serviceConfig3.setRef(new HelloServiceImpl());
            serviceConfig3.setVersion(version3);
            // Because executor is not set for serviceConfig3, the default executor of serviceConfig3 is built using
            // the threadpool parameter of the protocolConfig ( FixedThreadpool , max threads is 200)
            serviceConfig3.setExecutor(null);

            // It takes effect only if [executor-management-mode=isolation] is configured
            ApplicationConfig applicationConfig = new ApplicationConfig("provider-app");
            applicationConfig.setExecutorManagementMode(EXECUTOR_MANAGEMENT_MODE_ISOLATION);

            providerBootstrap
                    .application(applicationConfig)
                    .registry(registryConfig)
                    // export with tri and dubbo protocol
                    .protocol(new ProtocolConfig("tri", 20001))
                    .protocol(new ProtocolConfig("dubbo", 20002))
                    .service(serviceConfig1)
                    .service(serviceConfig2)
                    .service(serviceConfig3);

            providerBootstrap.start();

            // Verify that the executor is the previously configured
            ApplicationModel applicationModel = providerBootstrap.getApplicationModel();
            ExecutorRepository repository = ExecutorRepository.getInstance(applicationModel);
            assert repository instanceof IsolationExecutorRepository;
            assert executor1.equals(repository.getExecutor(serviceConfig1.toUrl()));
            assert executor2.equals(repository.getExecutor(serviceConfig2.toUrl()));
            // the default executor of serviceConfig3 is built using the threadpool parameter of the protocol
            ThreadPoolExecutor executor3 = (ThreadPoolExecutor) repository.getExecutor(serviceConfig3.toUrl());
            assert executor3.getThreadFactory() instanceof NamedInternalThreadFactory;
            NamedInternalThreadFactory threadFactory3 = (NamedInternalThreadFactory) executor3.getThreadFactory();

            // consumer app start with dubbo protocol and rpc call
            consumerBootstrap1 = configConsumerBootstrapWithProtocol("dubbo", registryConfig);
            rpcInvoke(consumerBootstrap1);

            // consumer app start with tri protocol and rpc call
            consumerBootstrap2 = configConsumerBootstrapWithProtocol("tri", registryConfig);
            rpcInvoke(consumerBootstrap2);

            // Verify that when the provider accepts different service requests,
            // whether to use the respective executor(threadFactory) of different services to create threads
            AtomicInteger threadNum1 = threadFactory1.getThreadNum();
            AtomicInteger threadNum2 = threadFactory2.getThreadNum();
            AtomicInteger threadNum3 = threadFactory3.getThreadNum();
            assert threadNum1.get() == 11;
            assert threadNum2.get() == 101;
            assert threadNum3.get() == 201;

        } finally {
            if (consumerBootstrap1 != null) {
                consumerBootstrap1.destroy();
            }
            if (consumerBootstrap2 != null) {
                consumerBootstrap2.destroy();
            }
            if (providerBootstrap != null) {
                providerBootstrap.destroy();
            }
            FrameworkModel.destroyAll();
        }
    }


    private static void rpcInvoke(DubboBootstrap consumerBootstrap) {
        DemoService demoServiceV1 = consumerBootstrap.getCache().get(DemoService.class.getName() + ":" + version1);
        HelloService helloServiceV2 = consumerBootstrap.getCache().get(HelloService.class.getName() + ":" + version2);
        HelloService helloServiceV3 = consumerBootstrap.getCache().get(HelloService.class.getName() + ":" + version3);
        for (int i = 0; i < 250; i++) {
            String invocation = "hello, version = " + version1;
            String response = demoServiceV1.sayName(invocation);
            Assert.assertTrue(response.startsWith("say: " + invocation + " from DemoServiceExecutor"));
        }
        for (int i = 0; i < 250; i++) {
            String invocation = "hello, version = " + version2;
            String response = helloServiceV2.sayHello(invocation);
            Assert.assertTrue(response.startsWith("Hello, " + invocation + " from HelloServiceExecutor"));
        }
        for (int i = 0; i < 250; i++) {
            String invocation = "hello, version = " + version3;
            String response = helloServiceV3.sayHello(invocation);
            Assert.assertTrue(response.startsWith("Hello, " + invocation + " from DubboServerHandler"));
        }
    }

    private static DubboBootstrap configConsumerBootstrapWithProtocol(String protocol, RegistryConfig registryConfig) {
        DubboBootstrap consumerBootstrap;
        consumerBootstrap = DubboBootstrap.newInstance();
        consumerBootstrap.application("consumer-app")
                .registry(registryConfig)
                .reference(generateReferenceConsumer(DemoService.class, version1, protocol))
                .reference(generateReferenceConsumer(HelloService.class, version2, protocol))
                .reference(generateReferenceConsumer(HelloService.class, version3, protocol));
        consumerBootstrap.start();
        return consumerBootstrap;
    }

    private static <S> Consumer<ReferenceBuilder<S>> generateReferenceConsumer(Class interfaceClass, String versionNumber, String protocol) {
        return builder -> builder.interfaceClass(interfaceClass)
                .version(versionNumber)
                .protocol(protocol)
                .scope("remote");
    }

}
