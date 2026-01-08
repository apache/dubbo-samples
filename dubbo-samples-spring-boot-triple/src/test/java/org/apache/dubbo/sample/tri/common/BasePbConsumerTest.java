/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.dubbo.sample.tri.common;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.apache.dubbo.sample.tri.PbGreeter;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author earthchen
 * @date 2021/9/9
 **/
public abstract class BasePbConsumerTest {

    protected static PbGreeter delegate;

//    protected static DubboBootstrap appDubboBootstrap;

    @Test
    public void serverStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        delegate.greetServerStream(request, new StdoutStreamObserver<GreeterReply>("sayGreeterServerStream") {
            @Override
            public void onNext(GreeterReply data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void stream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        final StreamObserver<GreeterRequest> requestObserver = delegate.greetStream(new StdoutStreamObserver<GreeterReply>("sayGreeterStream") {
            @Override
            public void onNext(GreeterReply data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void unaryGreeter() {
        final GreeterReply reply = delegate.greet(GreeterRequest.newBuilder()
                .setName("name")
                .build());
        Assert.assertNotNull(reply);
    }

    @Test(expected = RpcException.class)
    public void clientSendLargeSizeHeader() {
        StringBuilder sb = new StringBuilder("a");
        for (int j = 0; j < 15; j++) {
            sb.append(sb);
        }
        sb.setLength(8000);
        RpcContext.getClientAttachment().setObjectAttachment("large-size-meta", sb.toString());
        delegate.greet(GreeterRequest.newBuilder().setName("meta").build());
        RpcContext.getClientAttachment().clearAttachments();
    }

    @Test
    public void attachmentTest() {
        final String key = "user-attachment";
        final String value = "attachment-value";
        RpcContext.removeClientAttachment();
        RpcContext.getClientAttachment().setAttachment(key, value);
        GreeterReply reply = delegate.greetWithAttachment(GreeterRequest.newBuilder().setName("meta").build());
        Assert.assertEquals("hello,meta",reply.getMessage());
        final String returned = (String) RpcContext.getServerContext().getObjectAttachment(key);
        Assert.assertEquals("hello," + value, returned);
    }

//    @AfterClass
//    public static void alterTest() {
//        appDubboBootstrap.stop();
//        DubboBootstrap.reset();
//    }

}
