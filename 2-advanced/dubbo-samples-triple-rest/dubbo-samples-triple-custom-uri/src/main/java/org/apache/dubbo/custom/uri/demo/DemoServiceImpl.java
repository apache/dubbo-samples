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

package org.apache.dubbo.custom.uri.demo;

import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.CompletableFuture;

@DubboService
public class DemoServiceImpl implements Greeter {

    @Override
    public HelloReply sayHelloWithPost(HelloRequest request) {
        String message = "POST Hello, " + request.getName() + "!";
        return HelloReply.newBuilder().setMessage(message).build();
    }

    @Override
    public CompletableFuture<HelloReply> sayHelloWithPostAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> sayHelloWithPost(request));
    }

    @Override
    public HelloReply updateGreeting(HelloRequest request) {
        if (request == null || request.getName() == null) {
            return HelloReply.newBuilder().setMessage("Request or name is missing").build();
        }

        String message = "Updated greeting to: " + request.getName();
        return HelloReply.newBuilder().setMessage(message).build();
    }

    @Override
    public CompletableFuture<HelloReply> updateGreetingAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> updateGreeting(request));
    }
}
