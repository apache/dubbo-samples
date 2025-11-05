/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.dubbo.samples.triple.reactor;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ReactorServerTest {

    private static GreeterService greeterService;

    @BeforeClass
    public static void init() {
        ReferenceConfig<GreeterService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(GreeterService.class);
        referenceConfig.setProtocol(CommonConstants.TRIPLE);
        referenceConfig.setProxy(CommonConstants.NATIVE_STUB);
        referenceConfig.setTimeout(10000);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("tri-reactor-stub-server"))
                .registry(new RegistryConfig("zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":" +
                        System.getProperty("zookeeper.port", "2181")))
                .reference(referenceConfig)
                .start();

        greeterService = referenceConfig.get();
    }

    @Test
    public void consumeOneToOne() {
        StepVerifier.create(greeterService.greetOneToOne(Mono.just(GreeterRequest.newBuilder().setName("request-1").build())))
                .expectNext(GreeterReply.newBuilder().setMessage("request-1 -> server get").build())
                .expectComplete()
                .verify(Duration.ofMinutes(3));
    }

    @Test
    public void consumeOneToMany() {
        StepVerifier.create(greeterService.greetOneToMany(
                        Mono.just(GreeterRequest.newBuilder().setName("1,2,3,4,5,6,7,8,9,10").build())))
                .expectNextCount(10)
                .expectComplete()
                .log()
                .verify(Duration.ofMinutes(3));
    }

    @Test
    public void consumeManyToOne() {
        StepVerifier.create(greeterService.greetManyToOne(
                        Flux.range(1, 10).map(num -> GreeterRequest.newBuilder().setName(String.valueOf(num)).build())))
                .expectNext(GreeterReply.newBuilder().setMessage("55").build())
                .expectComplete()
                .verify(Duration.ofMinutes(3));
    }

    @Test
    @Ignore
    public void consumeManyToMany() {
        StepVerifier.create(greeterService.greetManyToMany(Flux.range(1, 10)
                        .map(num -> GreeterRequest.newBuilder().setName(String.valueOf(num)).build())))
                .expectNext(GreeterReply.newBuilder().setMessage("1 -> server get").build())
                .expectNextCount(9)
                .expectComplete()
                .verify(Duration.ofMinutes(3));
    }
}
