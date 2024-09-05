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

package org.apache.dubbo.demo;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {DubboAutoConfiguration.class})
@RunWith(SpringRunner.class)
public class WrapperConsumerIT {

    @DubboReference
    private GreeterWrapperService greeterService;

    private static HelloRequest buildRequest(String name) {
        HelloRequest request = new HelloRequest();
        request.setName(name);
        return request;
    }

    @Test
    public void sayHello() {
        HelloReply reply = greeterService.sayHello(buildRequest("world"));
        Assert.assertEquals("Hello world", reply.getMessage());
    }

    @Test
    public void sayHelloAsync() throws Exception {
        String reply = greeterService.sayHelloAsync("world").get(1, TimeUnit.SECONDS);
        Assert.assertEquals("Hello world", reply);
    }

    @Test
    public void sayHelloServerStream() throws Exception {
        CompletableFuture<Void> future = new CompletableFuture<>();
        AtomicInteger count = new AtomicInteger();
        StreamObserver<HelloReply> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(HelloReply reply) {
                Assert.assertEquals("Hello stream", reply.getMessage());
                count.incrementAndGet();
            }

            @Override
            public void onError(Throwable t) {
                future.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
                future.complete(null);
            }
        };
        greeterService.sayHelloServerStream(buildRequest("stream"), responseObserver);
        future.get(5, TimeUnit.SECONDS);
        Assert.assertEquals(5, count.get());
    }

    @Test
    public void sayHelloBiStream() throws Exception {
        CompletableFuture<Void> future = new CompletableFuture<>();
        AtomicInteger count = new AtomicInteger();
        StreamObserver<HelloReply> biResponseObserver = new StreamObserver<>() {
            @Override
            public void onNext(HelloReply reply) {
                Assert.assertEquals("Hello biStream", reply.getMessage());
                count.incrementAndGet();
            }

            @Override
            public void onError(Throwable t) {
                future.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
                future.complete(null);
            }
        };
        StreamObserver<HelloRequest> biRequestObserver = greeterService.sayHelloBiStream(biResponseObserver);
        for (int i = 0; i < 5; i++) {
            biRequestObserver.onNext(buildRequest("biStream"));
        }
        biRequestObserver.onCompleted();
        future.get(5, TimeUnit.SECONDS);
        Assert.assertEquals(5, count.get());
    }
}
