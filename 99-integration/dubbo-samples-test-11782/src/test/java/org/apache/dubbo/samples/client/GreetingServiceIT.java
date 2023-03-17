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
import org.apache.dubbo.qos.command.impl.Ls;
import org.apache.dubbo.remoting.exchange.support.header.HeaderExchangeClient;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.api.GreetingsService;
import org.apache.dubbo.samples.api.QosService;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

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

        new Thread(() -> service.sayHi("dubbo")).start();

        qosService.offline();

        Awaitility.await().atMost(60, TimeUnit.SECONDS).until(() -> new Ls(FrameworkModel.defaultModel()).listConsumer().contains("I-0,A-0"));

        ConsumerFilter.release();


        Awaitility.await().atMost(60, TimeUnit.SECONDS).until(() -> HeaderExchangeClient.IDLE_CHECK_TIMER.get().pendingTimeouts() == 0);
    }
}
