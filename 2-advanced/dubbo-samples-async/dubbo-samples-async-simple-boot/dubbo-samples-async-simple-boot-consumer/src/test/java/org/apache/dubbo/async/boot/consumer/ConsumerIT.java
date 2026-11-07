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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @date: 25/3/2023
 * @time: 5:59 PM
 */
@SpringBootTest
public class ConsumerIT {

    @DubboReference(async = true)
    private HiService hiService;

    @Test
    public void testCompletableFuture() throws Exception {
        hiService.sayHello("world");
        CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();

        assertNotNull(helloFuture);
        assertEquals("hi, your name is: world", helloFuture.get(5, TimeUnit.SECONDS));
    }

    @Test
    public void testAsyncCall() throws Exception {
        CompletableFuture<String> f = RpcContext.getContext().asyncCall(() -> hiService.sayHello("async call request"));
        assertEquals("hi, your name is: async call request", f.get(5, TimeUnit.SECONDS));
    }
}
