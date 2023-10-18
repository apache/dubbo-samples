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

import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.api.GreetingsService;

public class Application {
    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");
    private static final String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;

    public static void main(String[] args) {
        ServiceConfig<GreetingsService> service1 = new ServiceConfig<>();
        service1.setInterface(GreetingsService.class);
        service1.setRef(new GreetingsServiceImpl());
        service1.setVersion("hessian2");
        service1.setSerialization("hessian2");

        ServiceConfig<GreetingsService> service2 = new ServiceConfig<>();
        service2.setInterface(GreetingsService.class);
        service2.setRef(new GreetingsServiceImpl());
        service2.setVersion("fastjson2");
        service2.setSerialization("fastjson2");

        DubboBootstrap.getInstance()
                .application("first-dubbo-provider")
                .registry(new RegistryConfig(ZOOKEEPER_ADDRESS))
                .protocol(new ProtocolConfig("tri", -1))
                .service(service1)
                .service(service2)
                .start()
                .await();
    }
}
