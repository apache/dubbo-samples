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

package org.apache.dubbo.samples.provider;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.qos.command.impl.Ls;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.api.GreetingsService;
import org.apache.dubbo.samples.api.QosService;

import static org.awaitility.Awaitility.await;

public class Application {
    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");
    private static final String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("dubbo.application.metadata.publish.delay", "1");

        ServiceConfig<GreetingsService> service1 = new ServiceConfig<>();
        service1.setInterface(GreetingsService.class);
        service1.setVersion("auto");
        service1.setRef(new GreetingsServiceImpl());

        ServiceConfig<QosService> qosService = new ServiceConfig<>();
        qosService.setInterface(QosService.class);
        qosService.setRef(new QosServiceImpl());

        ServiceConfig<GreetingsService> service2 = new ServiceConfig<>();
        service2.setInterface(GreetingsService.class);
        service2.setVersion("deployer");
        service2.setRef(new GreetingsServiceImpl());

        ServiceConfig<GreetingsService> service3 = new ServiceConfig<>();
        service3.setInterface(GreetingsService.class);
        service3.setVersion("manual");
        service3.setDelay(-1);
        service3.setRef(new GreetingsServiceImpl());

        ServiceConfig<GreetingsService> service4 = new ServiceConfig<>();
        service4.setInterface(GreetingsService.class);
        service4.setVersion("delay");
        service4.setDelay(1000);
        service4.setRef(new GreetingsServiceImpl());

        ServiceConfig<GreetingsService> service5 = new ServiceConfig<>();
        service5.setInterface(GreetingsService.class);
        service5.setVersion("register-false");
        service5.setRegister(false);
        service5.setRef(new GreetingsServiceImpl());

        ServiceConfig<GreetingsService> service6 = new ServiceConfig<>();
        service6.setInterface(GreetingsService.class);
        service6.setVersion("registry-register-false");
        RegistryConfig registryConfig = new RegistryConfig(ZOOKEEPER_ADDRESS);
        registryConfig.setRegister(false);
        registryConfig.setDefault(false);
        service6.setRegistry(registryConfig);
        service6.setRef(new GreetingsServiceImpl());

        ApplicationConfig applicationConfig = new ApplicationConfig("first-dubbo-provider");

        DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance()
                .application(applicationConfig)
                .registry(new RegistryConfig(ZOOKEEPER_ADDRESS))
                .protocol(new ProtocolConfig("dubbo", -1))
                .service(qosService)
                .service(service2)
                .service(service3)
                .service(service4)
                .service(service5)
                .service(service6)
                .start();

        service1.export();

        await().until(()->{
            String result = new Ls(FrameworkModel.defaultModel()).execute(null, null);
            System.out.println(result);
            for (String s : result.split("\n")) {
                if (s.contains("manual")) {
                    if (!s.contains("zookeeper-A(N)/zookeeper-I(N)")) {
                        return false;
                    }
                } else if (s.contains("register-false")) {
                    if (s.contains("zookeeper")) {
                        return false;
                    }
                } else if (s.contains("GreetingsService")) {
                    if (!s.contains("zookeeper-A(Y)/zookeeper-I(Y)")) {
                        return false;
                    }
                }
            }
            return true;
        });

        if (DemoModuleDeployListener.isFailed()) {
            throw new IllegalStateException("Failed to deploy demo module");
        }

        dubboBootstrap.await();
    }
}
