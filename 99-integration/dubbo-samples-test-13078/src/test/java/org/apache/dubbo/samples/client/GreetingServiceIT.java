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
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.api.GreetingsService;

import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2LocalFlowController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class GreetingServiceIT {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    @Test
    public void test() {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(GreetingsService.class);
        GreetingsService service = reference.get();

        for (int i = 0; i < 100; i++) {
            String message = service.sayHi("dubbo");
            Assertions.assertEquals("hi, dubbo", message);
        }

        List<Http2Connection.Endpoint<Http2LocalFlowController>> endpoints = WireProtocolWrapper.getEndpoints();
        Assertions.assertEquals(1, endpoints.size());
        Http2Connection.Endpoint<Http2LocalFlowController> endpoint = endpoints.get(0);
        while (endpoint.incrementAndGetNextStreamId() > 0) {
            endpoint.incrementAndGetNextStreamId();
        }

        try {
            service.sayHi("dubbo");
            Assertions.fail();
        } catch (RpcException e) {
            Assertions.assertEquals("java.util.concurrent.ExecutionException: org.apache.dubbo.rpc.StatusRpcException: INTERNAL : Http2 exception", e.getMessage());
        }

        await().atMost(60, TimeUnit.SECONDS).untilAsserted(
                () -> Assertions.assertEquals(2, endpoints.size()));

        for (int i = 0; i < 100; i++) {
            String message = service.sayHi("dubbo");
            Assertions.assertEquals("hi, dubbo", message);
        }
    }
}
