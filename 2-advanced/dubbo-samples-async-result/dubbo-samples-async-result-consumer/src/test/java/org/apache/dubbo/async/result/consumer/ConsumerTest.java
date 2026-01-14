/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.async.result.consumer;

import org.apache.dubbo.ConsumerApplication;
import org.apache.dubbo.HiConsumer;
import org.apache.dubbo.HiService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = ConsumerApplication.class)
public class ConsumerTest {
    private static final Logger log = LoggerFactory.getLogger(ConsumerTest.class);

    @Autowired
    private HiConsumer consumer;

    @DubboReference(async = true, check = false)
    private HiService hiService;

    @DubboReference(check = false)
    private HiService hiServiceSync;

    @Test
    public void testAsyncCallMethod1WithFilterChain() throws Exception {
        CompletableFuture<String> future = hiService.sayHelloAsync("Alice");
        if (future == null) {
            throw new RuntimeException("Future should not be null");
        }
        String result = future.get(5000, TimeUnit.MILLISECONDS);

        if (result == null) {
            throw new RuntimeException("Result should not be null");
        }

        if (!result.contains("[short-circuit]")) {
            throw new RuntimeException("Should contain AsyncShortCircuitFilter short-circuit identifier");
        }

        if (!result.contains("sayHelloAsync")) {
            throw new RuntimeException("Should contain method name");
        }

        if (result.contains("[decorated]") || result.contains("[attach&decorated]") || result.contains("[Event-driven]")) {
            throw new RuntimeException("In short-circuit mode, subsequent Filters should not be executed");
        }
        log.info("successfully");
    }

    @Test
    public void testAsyncCallMethod2WithAttachment() throws Exception {

        String ignored = hiService.sayHello("Bob");
        log.info("Sync return value: {}", ignored);

        CompletableFuture<Object> future = RpcContext.getServiceContext().getCompletableFuture();
        if (future == null) {
            throw new RuntimeException("Should be able to get Future from RpcContext");
        }

        Object result = future.get(5000, TimeUnit.MILLISECONDS);

        if (!(result instanceof String)) {
            throw new RuntimeException("Result should be String type");
        }

        String resultStr = (String) result;
        if (!resultStr.contains("[short-circuit]")) {
            throw new RuntimeException("Should contain short-circuit identifier");
        }

        if (!resultStr.contains("sayHello")) {
            throw new RuntimeException("Should contain method name");
        }
        log.info("successfully");
    }

    @Test
    public void testHiConsumerAsyncMethods() throws Exception {
        consumer.asyncCallMethod1();
        Thread.sleep(2000);  // Wait for async callback to complete

        consumer.asyncCallMethod2();
        Thread.sleep(2000);  // Wait for async callback to complete
    }

    @Test
    public void testSyncCallWithAttachment() throws Exception {
        String result = hiServiceSync.sayHello("Charlie");
        log.info(" Sync call result: {}", result);

        if (!result.contains("[short-circuit]")) {
            throw new RuntimeException("Should contain short-circuit identifier");
        }

        if (!result.contains("sayHello")) {
            throw new RuntimeException("Should contain method name");
        }

        if (result.contains("[decorated]") || result.contains("[attach&decorated]") || result.contains("[Event-driven]")) {
            throw new RuntimeException("In short-circuit mode, subsequent Filters should not be executed");
        }
        log.info("successfully");
    }

    @Test
    public void testCompareDemo() throws Exception {
        consumer.compareDemo();
        Thread.sleep(3000);
    }

    @Test
    public void testFilterChainOrder() throws Exception {
        CompletableFuture<String> future = hiService.sayHelloAsync("FilterChainTest");
        String result = future.get(5000, TimeUnit.MILLISECONDS);

        log.info("Complete result: {}", result);

        int shortCircuitIdx = result.indexOf("[short-circuit]");
        int decoratedIdx = result.indexOf("[decorated]");
        int attachDecoratedIdx = result.indexOf("[attach&decorated]");
        int eventDrivenIdx = result.indexOf("[Event-driven]");

        if (shortCircuitIdx < 0) {
            throw new RuntimeException("Should contain short-circuit identifier");
        }

        if (decoratedIdx >= 0 || attachDecoratedIdx >= 0 || eventDrivenIdx >= 0) {
            throw new RuntimeException("In short-circuit mode, subsequent Filters should not execute");
        }
        log.info("successfully");
    }

    @Test
    public void testConcurrentAsyncCalls() throws Exception {

        @SuppressWarnings("unchecked")
        CompletableFuture<String>[] futures = new CompletableFuture[5];
        for (int i = 0; i < 5; i++) {
            String name = "User" + i;
            futures[i] = hiService.sayHelloAsync(name);
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
        allOf.get(10000, TimeUnit.MILLISECONDS);

        for (int i = 0; i < 5; i++) {
            String result = futures[i].get();
            if (result == null) {
                throw new RuntimeException("Result #" + i + " should not be null");
            }

            if (!result.contains("[short-circuit]")) {
                throw new RuntimeException("Result #" + i + " should contain short-circuit identifier");
            }

            if (!result.contains("sayHelloAsync")) {
                throw new RuntimeException("Result #" + i + " should contain method name");
            }

            log.info("Async call #{} result: {}", i, result);
        }

        log.info("successfully");
    }

}
