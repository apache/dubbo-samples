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

package org.apache.dubbo.samples.test.impl;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.test.api.DemoService;
import org.apache.dubbo.samples.test.api.DemoService2;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DemoServiceImpl implements DemoService {
    private final DemoService2 demoService2;

    public DemoServiceImpl(DemoService2 demoService2) {
        this.demoService2 = demoService2;
    }

    @Override
    public String commonInvoke(String param) {
        String randomStr1 = RandomUtil.randomString(1000);
        if (!randomStr1.equals(demoService2.commonInvoke(randomStr1))) {
            throw new IllegalStateException("commonInvoke failed");
        }

        String randomStr2 = RandomUtil.randomString(1000);
        try {
            if (!randomStr2.equals(demoService2.asyncInvoke(randomStr2).get())) {
                throw new IllegalStateException("commonInvoke failed");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        String randomStr3 = RandomUtil.randomString(1000);
        try {
            if (!randomStr3.equals(RpcContext.getClientAttachment().asyncCall(() -> demoService2.commonInvoke(randomStr3)).get())) {
                throw new IllegalStateException("commonInvoke failed");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        String randomStr4 = RandomUtil.randomString(1000);
        try {
            if (!randomStr4.equals(RpcContext.getClientAttachment().asyncCall(
                    () -> demoService2.asyncInvoke(randomStr4).get()).get())) {
                throw new IllegalStateException("commonInvoke failed");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return param;
    }

    @Override
    public CompletableFuture<String> asyncInvoke(String param) {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            String randomStr1 = RandomUtil.randomString(1000);
            if (!randomStr1.equals(demoService2.commonInvoke(randomStr1))) {
                throw new IllegalStateException("commonInvoke failed");
            }

            String randomStr2 = RandomUtil.randomString(1000);
            try {
                if (!randomStr2.equals(demoService2.asyncInvoke(randomStr2).get())) {
                    throw new IllegalStateException("commonInvoke failed");
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            String randomStr3 = RandomUtil.randomString(1000);
            try {
                if (!randomStr3.equals(RpcContext.getClientAttachment().asyncCall(() -> demoService2.commonInvoke(randomStr3)).get())) {
                    throw new IllegalStateException("commonInvoke failed");
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            String randomStr4 = RandomUtil.randomString(1000);
            try {
                if (!randomStr4.equals(RpcContext.getClientAttachment().asyncCall(
                        () -> demoService2.asyncInvoke(randomStr4).get()).get())) {
                    throw new IllegalStateException("commonInvoke failed");
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            future.complete(param);
        }).start();
        return future;
    }
}
