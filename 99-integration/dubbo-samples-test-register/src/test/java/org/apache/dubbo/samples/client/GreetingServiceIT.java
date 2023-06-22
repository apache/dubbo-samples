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

package org.apache.dubbo.samples.client;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.api.GreetingsService;
import org.apache.dubbo.samples.api.QosService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.awaitility.Awaitility.await;

class GreetingServiceIT {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    @Test
    void test() {
        ReferenceConfig<GreetingsService> reference1 = new ReferenceConfig<>();
        reference1.setInterface(GreetingsService.class);
        reference1.setVersion("manual");
        reference1.setCheck(false);

        ReferenceConfig<GreetingsService> reference2 = new ReferenceConfig<>();
        reference2.setInterface(GreetingsService.class);
        reference2.setVersion("delay");

        ReferenceConfig<GreetingsService> reference3 = new ReferenceConfig<>();
        reference3.setInterface(GreetingsService.class);
        reference3.setVersion("deployer");

        ReferenceConfig<GreetingsService> reference4 = new ReferenceConfig<>();
        reference4.setInterface(GreetingsService.class);
        reference4.setVersion("auto");

        ReferenceConfig<GreetingsService> reference5 = new ReferenceConfig<>();
        reference5.setInterface(GreetingsService.class);
        reference5.setVersion("register-false");
        reference5.setCheck(false);

        ReferenceConfig<GreetingsService> reference6 = new ReferenceConfig<>();
        reference6.setInterface(GreetingsService.class);
        reference6.setVersion("registry-register-false");
        reference6.setCheck(false);

        ReferenceConfig<QosService> qosServiceRef = new ReferenceConfig<>();
        qosServiceRef.setInterface(QosService.class);

        DubboBootstrap.getInstance()
                .application(new ApplicationConfig("first-dubbo-consumer"))
                .registry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181?enable-empty-protection=false"))
                .reference(reference1)
                .reference(reference2)
                .reference(reference3)
                .reference(reference4)
                .reference(reference5)
                .reference(reference6)
                .reference(qosServiceRef)
                .start();

        GreetingsService manual = reference1.get();
        GreetingsService delay = reference2.get();
        GreetingsService deployer = reference3.get();
        GreetingsService auto = reference4.get();
        GreetingsService registerFalse = reference5.get();
        GreetingsService registryRegisterFalse = reference6.get();

        Assertions.assertThrows(RpcException.class, () -> manual.sayHi("test"));
        Assertions.assertEquals("hi, delay", delay.sayHi("delay"));
        Assertions.assertEquals("hi, deployer", deployer.sayHi("deployer"));
        Assertions.assertEquals("hi, auto", auto.sayHi("auto"));
        Assertions.assertThrows(RpcException.class, () -> registerFalse.sayHi("test"));
        Assertions.assertThrows(RpcException.class, () -> registryRegisterFalse.sayHi("test"));

        QosService qosService = qosServiceRef.get();
        qosService.online();

        await().untilAsserted(()->Assertions.assertEquals("hi, manual", manual.sayHi("manual")));
        await().untilAsserted(()->Assertions.assertEquals("hi, register-false", registerFalse.sayHi("register-false")));
        await().untilAsserted(()->Assertions.assertEquals("hi, registry-register-false", registryRegisterFalse.sayHi("registry-register-false")));
    }
}
