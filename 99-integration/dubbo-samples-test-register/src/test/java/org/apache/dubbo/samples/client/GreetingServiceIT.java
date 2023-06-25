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

        ReferenceConfig<GreetingsService> reference7 = new ReferenceConfig<>();
        reference7.setInterface(GreetingsService.class);
        reference7.setVersion("register-false-delay");
        reference7.setCheck(false);

        ReferenceConfig<GreetingsService> reference8 = new ReferenceConfig<>();
        reference8.setInterface(GreetingsService.class);
        reference8.setVersion("register-false-delay-manual");
        reference8.setCheck(false);

        ReferenceConfig<GreetingsService> reference9 = new ReferenceConfig<>();
        reference9.setInterface(GreetingsService.class);
        reference9.setVersion("registry-register-false-delay");
        reference9.setCheck(false);

        ReferenceConfig<GreetingsService> reference10 = new ReferenceConfig<>();
        reference10.setInterface(GreetingsService.class);
        reference10.setVersion("registry-register-false-delay-manual");
        reference10.setCheck(false);

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
                .reference(reference7)
                .reference(reference8)
                .reference(reference9)
                .reference(reference10)
                .reference(qosServiceRef)
                .start();

        GreetingsService manual = reference1.get();
        GreetingsService delay = reference2.get();
        GreetingsService deployer = reference3.get();
        GreetingsService auto = reference4.get();
        GreetingsService registerFalse = reference5.get();
        GreetingsService registryRegisterFalse = reference6.get();
        GreetingsService registerFalseDelay = reference7.get();
        GreetingsService registerFalseDelayManual = reference8.get();
        GreetingsService registryRegisterFalseDelay = reference9.get();
        GreetingsService registryRegisterFalseDelayManual = reference10.get();

        Assertions.assertThrows(RpcException.class, () -> manual.sayHi("test"));
        Assertions.assertEquals("hi, delay", delay.sayHi("delay"));
        Assertions.assertEquals("hi, deployer", deployer.sayHi("deployer"));
        Assertions.assertEquals("hi, auto", auto.sayHi("auto"));
        Assertions.assertThrows(RpcException.class, () -> registerFalse.sayHi("test"));
        Assertions.assertThrows(RpcException.class, () -> registryRegisterFalse.sayHi("test"));
        Assertions.assertThrows(RpcException.class, () -> registerFalseDelay.sayHi("test"));
        Assertions.assertThrows(RpcException.class, () -> registerFalseDelayManual.sayHi("test"));
        Assertions.assertThrows(RpcException.class, () -> registryRegisterFalseDelay.sayHi("test"));
        Assertions.assertThrows(RpcException.class, () -> registryRegisterFalseDelayManual.sayHi("test"));


        QosService qosService = qosServiceRef.get();
        qosService.online();

        await().untilAsserted(() -> {
            String result;
            try {
                result = manual.sayHi("manual");
            } catch (Throwable t) {
                result = "";
            }
            Assertions.assertEquals("hi, manual", result);
        });

        await().untilAsserted(() -> {
            String result;
            try {
                result = registerFalse.sayHi("register-false");
            } catch (Throwable t) {
                result = "";
            }
            Assertions.assertEquals("hi, register-false", result);
        });
        await().untilAsserted(() -> {
            String result;
            try {
                result = registryRegisterFalse.sayHi("registry-register-false");
            } catch (Throwable t) {
                result = "";
            }
            Assertions.assertEquals("hi, registry-register-false", result);
        });
        await().untilAsserted(() -> {
            String result;
            try {
                result = registerFalseDelay.sayHi("registry-register-delay");
            } catch (Throwable t) {
                result = "";
            }
            Assertions.assertEquals("hi, registry-register-delay", result);
        });
        await().untilAsserted(() -> {
            String result;
            try {
                result = registerFalseDelayManual.sayHi("registry-register-delay-manual");
            } catch (Throwable t) {
                result = "";
            }
            Assertions.assertEquals("hi, registry-register-delay-manual", result);
        });
        await().untilAsserted(() -> {
            String result;
            try {
                result = registryRegisterFalseDelay.sayHi("registry-register-false-delay");
            } catch (Throwable t) {
                result = "";
            }
            Assertions.assertEquals("hi, registry-register-false-delay", result);
        });
        await().untilAsserted(() -> {
            String result;
            try {
                result = registryRegisterFalseDelayManual.sayHi("registry-register-false-delay-manual");
            } catch (Throwable t) {
                result = "";
            }
            Assertions.assertEquals("hi, registry-register-false-delay-manual", result);
        });
    }
}
