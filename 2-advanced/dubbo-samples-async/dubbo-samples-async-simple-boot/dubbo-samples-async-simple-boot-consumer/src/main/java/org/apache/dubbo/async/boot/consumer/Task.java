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
package org.apache.dubbo.async.boot.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.async.boot.HiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @date: 24/3/2023
 * @time: 10:21 PM
 */
@Component
public class Task implements CommandLineRunner {
    @DubboReference(async = true)
    private HiService hiService;

    @Override
    public void run(String... args) throws Exception {
        hiService.sayHello("world");//调用远程hiService 的sayHello

        CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();
        helloFuture.whenComplete((retValue, exception) -> {
            if (exception == null) {
                System.out.println("return value: " + retValue);
            } else {
                exception.printStackTrace();
            }
        });

        CompletableFuture<String> f = RpcContext.getContext().asyncCall(() -> hiService.sayHello("async call request"));
        System.out.println("async call returned: " + f.get());

        RpcContext.getContext().asyncCall(() -> {
            hiService.sayHello("one way call request1");
        });
    }
}
