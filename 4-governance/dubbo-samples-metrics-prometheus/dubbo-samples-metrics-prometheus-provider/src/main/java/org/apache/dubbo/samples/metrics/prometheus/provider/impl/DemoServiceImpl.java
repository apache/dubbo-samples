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

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.metrics.prometheus.api.DemoService;
import org.apache.dubbo.samples.metrics.prometheus.api.model.Result;
import org.apache.dubbo.samples.metrics.prometheus.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@DubboService
public class DemoServiceImpl implements DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
    private String name = "Dubbo ~";

    @Override
    public CompletableFuture<Integer> sayHello() {
        return CompletableFuture.completedFuture(2122);
    }

    @Override
    public Result sayHello(String localName) {
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return new Result(name, "Hello " + localName + ", resp from provider: " +
                RpcContext.getContext().getLocalAddress());
    }

    @Override
    public Result sayHello(final Long id, String name) {
        return sayHello(new User(id, name));
    }

    @Override
    public Result sayHello(User user) {
        String localName = user.getUsername();
        Long id = user.getId();
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello "
                + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return new Result(name, "Hello " + id + " " + localName +
                ", resp from provider: " + RpcContext.getContext().getLocalAddress());

    }

    @Override
    public String stringArray(String[] bytes) {
        return bytes.toString();
    }

}
