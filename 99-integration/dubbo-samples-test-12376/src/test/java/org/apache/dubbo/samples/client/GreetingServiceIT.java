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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GreetingServiceIT {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    @Test
    void test() {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setInterface(GreetingsService.class);

        ReferenceConfig<QosService> qosReference = new ReferenceConfig<>();
        qosReference.setInterface(QosService.class);

        DubboBootstrap.getInstance()
                .application(new ApplicationConfig("first-dubbo-consumer"))
                .registry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181?enable-empty-protection=false"))
                .reference(reference)
                .reference(qosReference)
                .start();

        QosService qosService = qosReference.get();

        GreetingsService service = reference.get();

        for (int i = 0; i < 100; i++) {
            Assertions.assertEquals("hi, dubbo", service.sayHi("dubbo"));
        }

        Assertions.assertTrue(qosService.expected());
        Assertions.assertTrue(ConsumerAlibabaFilter.expected());
        Assertions.assertTrue(ConsumerClusterFilter.expected());
        Assertions.assertTrue(ConsumerFilter.expected());
    }
}
