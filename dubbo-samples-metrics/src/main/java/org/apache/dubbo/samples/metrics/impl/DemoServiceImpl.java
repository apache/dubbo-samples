/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.samples.metrics.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.samples.metrics.api.DemoService;
import org.apache.dubbo.samples.metrics.model.Result;
import org.apache.dubbo.samples.metrics.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class DemoServiceImpl implements DemoService {

    public static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    private String name = "Han MeiMei";

    @Override
    public CompletableFuture<Integer> sayHello() {
        return CompletableFuture.completedFuture(2122);
    }



    @Override
    public Result sayHello(String localName) {
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext
                .getContext().getRemoteAddress());
        return new Result(name, "Hello " + localName + ", response from provider: " + RpcContext.getContext().getLocalAddress());
    }

    @Override
    public Result sayHello(final Long id, final String name) {
        return sayHello(new User(id, name));
    }

    @Override
    public Result sayHello(final User user) {
        String localName = user.getUsername();
        Long id = user.getId();
        logger.info("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext
                .getContext().getRemoteAddress());
        return new Result(name, "Hello " + id + " " + localName +
//                " " + "bytes: " + user.getBytes().toString() +
                ", response from provider: " + RpcContext.getContext().getLocalAddress());
    }

    @Override
    public String stringArray(String[] bytes) {
        return bytes.toString();
    }
}
