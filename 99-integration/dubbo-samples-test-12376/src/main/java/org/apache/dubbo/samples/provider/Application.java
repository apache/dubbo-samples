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
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.api.BackendService;
import org.apache.dubbo.samples.api.GreetingsService;
import org.apache.dubbo.samples.api.HelloService;
import org.apache.dubbo.samples.api.QosService;

public class Application {
    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");
    private static final String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;

    public static void main(String[] args) {
        System.setProperty("dubbo.application.metadata.publish.delay", "1");
        DubboBootstrap dubboBootstrap1 = export(20881);
        DubboBootstrap dubboBootstrap2 = export(20882);
        DubboBootstrap dubboBootstrap3 = export(20883);

        dubboBootstrap1.await();
    }

    private static DubboBootstrap export(int port) {
        DubboBootstrap dubboBootstrap;
        FrameworkModel frameworkModel = new FrameworkModel();
        ApplicationModel applicationModel = frameworkModel.newApplication();

        ServiceConfig<BackendService> backendService = new ServiceConfig<>();
        backendService.setInterface(BackendService.class);
        backendService.setRef(new BackendServiceImpl());

        ServiceConfig<QosService> qosService = new ServiceConfig<>();
        qosService.setInterface(QosService.class);
        qosService.setRef(new QosServiceImpl());
        qosService.setVersion(String.valueOf(port));

        ReferenceConfig<BackendService> backendRef = new ReferenceConfig<>();
        backendRef.setInterface(BackendService.class);
        backendRef.setLoadbalance("alibaba-test");
        backendRef.setScope("remote");

        ApplicationConfig applicationConfig = new ApplicationConfig("first-dubbo-provider");
        applicationConfig.setRegisterMode("interface");

        dubboBootstrap = DubboBootstrap.getInstance(applicationModel)
                .application(applicationConfig)
                .registry(new RegistryConfig(ZOOKEEPER_ADDRESS))
                .protocol(new ProtocolConfig("dubbo", port))
                .service(backendService)
                .service(qosService)
                .reference(backendRef)
                .start();

        ServiceConfig<HelloService> helloService = new ServiceConfig<>(applicationModel.getDefaultModule());
        helloService.setInterface(HelloService.class);
        helloService.setRef(new HelloServiceImpl(backendRef.get()));
        helloService.export();

        ReferenceConfig<HelloService> helloRef = new ReferenceConfig<>(applicationModel.getDefaultModule());
        helloRef.setInterface(HelloService.class);
        helloRef.setLoadbalance("apache-test");
        helloRef.setScope("remote");

        ServiceConfig<GreetingsService> greetService = new ServiceConfig<>(applicationModel.getDefaultModule());
        greetService.setInterface(GreetingsService.class);
        greetService.setRef(new GreetingsServiceImpl(helloRef.get()));
        greetService.export();
        return dubboBootstrap;
    }
}
