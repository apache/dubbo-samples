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
import org.apache.dubbo.samples.api.GreetingsService;
import org.apache.dubbo.samples.api.QosService;
import org.apache.dubbo.samples.filter.ConsumerAlibabaFilter;
import org.apache.dubbo.samples.filter.ConsumerClusterFilter;
import org.apache.dubbo.samples.filter.ConsumerFilter;
import org.apache.dubbo.samples.router.AlibabaRouter;
import org.apache.dubbo.samples.router.ApacheRouter;
import org.apache.dubbo.samples.router.ApacheStateRouter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GreetingServiceIT {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    @Test
    void test() {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setInterface(GreetingsService.class);

        ReferenceConfig<QosService> qosReference1 = new ReferenceConfig<>();
        qosReference1.setInterface(QosService.class);
        qosReference1.setVersion("20881");
        ReferenceConfig<QosService> qosReference2 = new ReferenceConfig<>();
        qosReference2.setInterface(QosService.class);
        qosReference2.setVersion("20882");
        ReferenceConfig<QosService> qosReference3 = new ReferenceConfig<>();
        qosReference3.setInterface(QosService.class);
        qosReference3.setVersion("20883");

        DubboBootstrap.getInstance()
                .application(new ApplicationConfig("first-dubbo-consumer"))
                .registry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181?enable-empty-protection=false"))
                .reference(reference)
                .reference(qosReference1)
                .reference(qosReference2)
                .reference(qosReference3)
                .start();

        QosService qosService1 = qosReference1.get();
        QosService qosService2 = qosReference2.get();
        QosService qosService3 = qosReference3.get();

        GreetingsService service = reference.get();

        for (int i = 0; i < 100; i++) {
            Assertions.assertEquals("hi, dubbo", service.sayHi("dubbo"));
        }

        Assertions.assertTrue(qosService1.expected());
        Assertions.assertTrue(qosService2.expected());
        Assertions.assertTrue(qosService3.expected());
        Assertions.assertTrue(ConsumerAlibabaFilter.expected());
        Assertions.assertTrue(ConsumerClusterFilter.expected());
        Assertions.assertTrue(ConsumerFilter.expected());

        Assertions.assertTrue(AlibabaRouter.isInvoked());
        Assertions.assertTrue(ApacheRouter.isInvoked());
        Assertions.assertTrue(ApacheStateRouter.isInvoked());
    }
}
