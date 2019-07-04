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

package org.apache.dubbo.samples.async;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.async.api.AsyncService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/async-consumer.xml"})
public class AsyncServiceIT {
    @Autowired
    private AsyncService asyncService;

    @Test
    public void testCompletableFuture() throws Exception {
        asyncService.sayHello("completable future");
        CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();
        CountDownLatch latch = new CountDownLatch(1);
        helloFuture.whenComplete((retValue, exception) -> {
            Assert.assertNull(exception);
            Assert.assertEquals("hello, completable future", retValue);
            latch.countDown();
        });

        latch.await();
    }

    @Test
    public void testAsyncCall() throws Exception {
        CompletableFuture<String> f = RpcContext.getContext().asyncCall(() -> asyncService.sayHello("async call"));
        Assert.assertEquals("hello, async call", f.get());
    }
}
