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

package org.apache.dubbo.samples.metrics.prometheus.provider.impl;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.metrics.prometheus.api.DemoService;
import org.apache.dubbo.samples.metrics.prometheus.api.DemoService2;
import org.apache.dubbo.samples.metrics.prometheus.api.model.Result;
import org.apache.dubbo.samples.metrics.prometheus.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class DemoServiceImpl2 implements DemoService2 {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl2.class);
    private String name = "Dubbo ~";

    private Random random = new Random(200);

    @Override
    public CompletableFuture<Integer> sayHello() {
        return CompletableFuture.completedFuture(2122);
    }

    @Override
    public Result sayHello(String localName) {
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        doAction();
        return new Result(name, "Hello " + localName + ", resp from provider2: " +
                RpcContext.getContext().getLocalAddress());
    }

    @Override
    public Result sayHello(final Long id, String name) {
        return sayHello(new User(id, name));
    }



    private void doAction() {
        try {
            Thread.sleep(random.nextInt(3000));
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Result sayHello(User user) {
        String localName = user.getUsername();
        Long id = user.getId();
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello "
                + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());

        doAction();
        return new Result(name, "Hello " + id + " " + localName +
                ", resp from provider2: " + RpcContext.getContext().getLocalAddress());

    }

    @Override
    public String stringArray(String[] bytes) {
        return bytes.toString();
    }

    @Override
    public Result timeLimitedMethod(String localName) throws InterruptedException {
        //Simulate a response timeout scenario
        Thread.sleep(5000);
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + localName +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return new Result(localName, "Hello " + localName + ", resp from provider2: " +
                RpcContext.getContext().getLocalAddress());
    }

    @Override
    public Result randomResponseTime(String localName) throws InterruptedException {
        Random random = new Random();
        int responseTime = Math.abs(random.nextInt() % 2999 + 1);
        Thread.sleep(responseTime);
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + localName +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return new Result(localName, "Hello " + localName + ", resp from provider2: " +
                RpcContext.getContext().getLocalAddress());
    }

    @Override
    public Result runTimeException(String localName) {
        int i = 1 / 0;
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + localName +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return new Result(localName, "Hello " + localName + ", resp from provider2: " +
                RpcContext.getContext().getLocalAddress());
    }

}
