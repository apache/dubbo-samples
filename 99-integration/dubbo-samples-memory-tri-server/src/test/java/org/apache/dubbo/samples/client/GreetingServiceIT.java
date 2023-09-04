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
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.api.GreetingsService;
import org.apache.dubbo.samples.api.QosService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class GreetingServiceIT {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    @Test
    void test() {
        ReferenceConfig<QosService> qosReference = new ReferenceConfig<>();
        qosReference.setInterface(QosService.class);

        DubboBootstrap.getInstance()
                .application(new ApplicationConfig("first-dubbo-consumer"))
                .registry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181?enable-empty-protection=false"))
                .reference(qosReference)
                .start();

        bench(100);

        QosService qosService = qosReference.get();
        long memoryStart = qosService.usedMemory();

        for (int i = 0; i < 10; i++) {
            bench(100);
            long endMemory = qosService.usedMemory();
            System.out.println("Used: " + endMemory);
            System.out.println("Delta: " + (endMemory - memoryStart));
            Assertions.assertTrue((endMemory - memoryStart) < 100000);
        }
    }

    private static void bench(int range) {
        IntStream.range(0, range)
                .parallel()
                .forEach((ignore)->{
                    FrameworkModel frameworkModel = new FrameworkModel();
                    ApplicationModel applicationModel = frameworkModel.newApplication();
                    ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
                    reference.setInterface(GreetingsService.class);

                    ApplicationConfig applicationConfig = new ApplicationConfig("first-dubbo-consumer");
                    applicationConfig.setMetadataType("remote");
                    applicationConfig.setQosEnable(false);
                    DubboBootstrap.getInstance(applicationModel)
                            .application(applicationConfig)
                            .registry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181?enable-empty-protection=false"))
                            .reference(reference)
                            .start();

                    GreetingsService service = reference.get();
                    for (int i = 0; i < 10; i++) {
                        service.sayHi("dubbo");
                    }
                    frameworkModel.destroy();
                });
    }
}
