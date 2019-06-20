/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.samples.generic.call.impl;

import org.apache.dubbo.samples.generic.call.api.GenericType;
import org.apache.dubbo.samples.generic.call.api.HelloService;
import org.apache.dubbo.samples.generic.call.api.Person;

import java.util.concurrent.CompletableFuture;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "sayHello: " + name;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete("sayHelloAsync: " + name);
        }).start();

        return future;
    }

    @Override
    public CompletableFuture<Person> sayHelloAsyncComplex(String name) {
        Person person = new Person(1, "sayHelloAsyncComplex: " + name);
        CompletableFuture<Person> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete(person);
        }).start();

        return future;
    }

    @Override
    public CompletableFuture<GenericType<Person>> sayHelloAsyncGenericComplex(String name) {
        Person person = new Person(1, "sayHelloAsyncGenericComplex: " + name);
        GenericType<Person> genericType = new GenericType<>(person);
        CompletableFuture<GenericType<Person>> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete(genericType);
        }).start();

        return future;
    }
}
