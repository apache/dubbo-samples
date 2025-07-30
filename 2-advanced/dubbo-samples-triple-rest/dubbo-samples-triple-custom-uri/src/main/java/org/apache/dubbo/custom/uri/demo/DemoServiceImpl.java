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
        String name = request.getName();
        String message = "Hello, " + (name.isEmpty() ? "World" : name) + "!";
        return HelloReply.newBuilder().setMessage(message).build();
    }

    @Override
    public HelloReply updateGreeting(HelloRequest request) {
        String name = request.getName();
        String message = "Greeting updated for: " + (name.isEmpty() ? "World" : name) + "!";
        return HelloReply.newBuilder().setMessage(message).build();
    }


    @Override
    public HelloReply healthCheck(HelloRequest request) {
        return HelloReply.newBuilder().setMessage("Health check successful!").build();
    }


    @Override
    public HelloReply checkName(HelloRequest request) {
        String name = request.getName();
        String message = "Name checked: " + (name.isEmpty() ? "No name provided" : name);
        return HelloReply.newBuilder().setMessage(message).build();
    }

    @Override
    public HelloReply simpleCheck(HelloRequest request) {
        return HelloReply.newBuilder().setMessage("Simple check successful!").build();
    }


    @Override
    public HelloReply actionCheck(HelloRequest request) {
        // 这里可以处理路径变量
        return HelloReply.newBuilder().setMessage("Action check successful!").build();
    }


    @Override
    public HelloReply actionCheckWithName(HelloRequest request) {
        String name = request.getName();
        String message = "Action check with name: " + (name.isEmpty() ? "No name provided" : name);
        return HelloReply.newBuilder().setMessage(message).build();
    }

    @Override
    public CompletableFuture<HelloReply> sayHelloWithPostAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            String name = request.getName();
            String message = "Hello, " + (name.isEmpty() ? "World" : name) + "!";
            return HelloReply.newBuilder().setMessage(message).build();
        });
    }

    @Override
    public CompletableFuture<HelloReply> updateGreetingAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            String name = request.getName();
            String message = "Greeting updated for: " + (name.isEmpty() ? "World" : name) + "!";
            return HelloReply.newBuilder().setMessage(message).build();
        });
    }

    @Override
    public CompletableFuture<HelloReply> healthCheckAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            return HelloReply.newBuilder().setMessage("Health check successful!").build();
        });
    }

    @Override
    public CompletableFuture<HelloReply> checkNameAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            String name = request.getName();
            String message = "Name checked: " + (name.isEmpty() ? "No name provided" : name);
            return HelloReply.newBuilder().setMessage(message).build();
        });
    }

    @Override
    public CompletableFuture<HelloReply> simpleCheckAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            return HelloReply.newBuilder().setMessage("Simple check successful!").build();
        });
    }

    @Override
    public CompletableFuture<HelloReply> actionCheckAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            // 这里可以处理路径变量
            return HelloReply.newBuilder().setMessage("Action check successful!").build();
        });
    }

    @Override
    public CompletableFuture<HelloReply> actionCheckWithNameAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            String name = request.getName();
            String message = "Action check with name: " + (name.isEmpty() ? "No name provided" : name);
            return HelloReply.newBuilder().setMessage(message).build();
        });
    }
}
