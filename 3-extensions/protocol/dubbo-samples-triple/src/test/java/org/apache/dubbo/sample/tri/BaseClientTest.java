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

package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.protocol.tri.CancelableStreamObserver;
import org.apache.dubbo.rpc.protocol.tri.ClientStreamObserver;
import org.apache.dubbo.sample.tri.util.StdoutStreamObserver;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * BaseClientTest
 * @date 2021/9/9
 **/
public abstract class BaseClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseClientTest.class);

    protected static Greeter delegate;

    protected static DubboBootstrap appDubboBootstrap;

    @Test
    public void serverStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        String key = "lastTimestamp";
        Map<String, Long> map = new HashMap<>();
        StreamObserver<GreeterReply> observer = new StdoutStreamObserver<GreeterReply>(
                "sayGreeterServerStream") {
            @Override
            public void onNext(GreeterReply data) {
                long lastTimestamp = map.getOrDefault(key, 0L);
                long now = System.currentTimeMillis();
                map.put(key, now);
                if (lastTimestamp == 0) {
                    latch.countDown();
                } else {
                    long diff = Math.abs(now - lastTimestamp - 1000);
                    System.out.println(diff);
                    if (diff < 1000) {
                        latch.countDown();
                    }
                }
                super.onNext(data);
            }
        };
        delegate.greetServerStream(request, observer);
        Assert.assertTrue(latch.await(12, TimeUnit.SECONDS));
    }


    @Test
    public void cancelServerStream() throws InterruptedException {
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        int n = 10;
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                LOGGER.info("Received <-{}", data);
                cancel(null);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }
        };
        delegate.cancelServerStream(request, observer);
        Thread.sleep(2000);
        GreeterReply reply = delegate.queryCancelResult(
                GreeterRequest.newBuilder()
                        .setName("cancelServerStream")
                        .build()
        );
        Assert.assertEquals("true", reply.getMessage());
    }


    @Test
    public void cancelBiStream() throws InterruptedException {
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                LOGGER.info("Received <-{}", data);
                cancel(null);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }
        };
        final StreamObserver<GreeterRequest> requestObserver =
                delegate.cancelBiStream(observer);
        CancelableStreamObserver<GreeterRequest> streamObserver =
                (CancelableStreamObserver<GreeterRequest>) requestObserver;
        streamObserver.onNext(request);
        Thread.sleep(1000);
        streamObserver.cancel(new RuntimeException());
        streamObserver.onCompleted();
        Thread.sleep(2000);
        GreeterReply reply = delegate.queryCancelResult(
                GreeterRequest.newBuilder()
                        .setName("cancelBiStream")
                        .build()
        );
        Assert.assertEquals("true", reply.getMessage());
    }


    @Test
    public void cancelBiStream2() throws InterruptedException {
        int n = 10;
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                LOGGER.info("Received:{}", data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }
        };
        final StreamObserver<GreeterRequest> requestObserver =
                delegate.cancelBiStream2(observer);
        CancelableStreamObserver<GreeterRequest> streamObserver =
                (CancelableStreamObserver<GreeterRequest>) requestObserver;
        for (int i = 0; i < n; i++) {
            streamObserver.onNext(request);
        }
        Thread.sleep(1000);
        streamObserver.cancel(null);
        // streamObserver.onCompleted();
        Thread.sleep(2000);
        GreeterReply reply = delegate.queryCancelResult(
                GreeterRequest.newBuilder()
                        .setName("cancelBiStream2")
                        .build()
        );
        Assert.assertEquals("true", reply.getMessage());
    }

    @Test
    public void compressorBiStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                LOGGER.info("Received data {}", data);
                latch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }
        };
        final ClientStreamObserver<GreeterRequest> requestObserver =
                (ClientStreamObserver<GreeterRequest>) delegate.compressorBiStream(observer);
        requestObserver.setCompression("gzip");
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }


    @Test
    public void clientCompressorBiStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                LOGGER.info("{}", data);
                latch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }
        };
        final ClientStreamObserver<GreeterRequest> requestObserver =
                (ClientStreamObserver<GreeterRequest>) delegate.clientCompressorBiStream(observer);
        requestObserver.setCompression("gzip");
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void serverCompressorBiStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                LOGGER.info("{}", data);
                latch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }
        };
        final ClientStreamObserver<GreeterRequest> requestObserver =
                (ClientStreamObserver<GreeterRequest>) delegate.serverCompressorBiStream(observer);
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }


    @Test
    public void stream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new StdoutStreamObserver<GreeterReply>(
                "tri pb stream") {
            @Override
            public void onNext(GreeterReply data) {
                super.onNext(data);
                latch.countDown();
            }
        };
        final StreamObserver<GreeterRequest> requestObserver =
                delegate.greetStream(observer);
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void unaryGreeterAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Map<String, Integer> map = new HashMap<>();
        map.put("val", 1);
        final CompletableFuture<GreeterReply> future = delegate.greetAsync(GreeterRequest.newBuilder()
                .setName("name")
                .build());
        future.whenComplete((s, throwable) -> {
            System.out.println(s);
            if ("hello,name".equals(s.getMessage())) {
                map.put("val", map.get("val") + 1);
            }
            latch.countDown();
        });
        map.put("val", 2);
        latch.await(3, TimeUnit.SECONDS);
        Assert.assertEquals(3, map.get("val").intValue());
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
        for (int j = 0; j < 17; j++) {
            sb.append(sb);
        }
        RpcContext.getClientAttachment().setObjectAttachment("large-size-meta", sb.toString());
        delegate.greet(GreeterRequest.newBuilder().setName("meta").build());
        RpcContext.getClientAttachment().clearAttachments();
    }


    // @Test(expected = RpcException.class)
    @Test
    @Ignore
    public void serverSendLargeSizeHeader() {
        final String key = "user-attachment";
        GreeterReply reply =
                delegate.greetReturnBigAttachment(GreeterRequest.newBuilder().setName("meta").build());
        final String returned = (String) RpcContext.getServerContext().getObjectAttachment(key);
        Assert.assertNotNull(returned);
    }

    @Test
    public void attachmentTest() {
        final String key = "user-attachment";
        final String key2 = "Test";
        final String value2 = "Value";
        final String value = "attachment-value";
        RpcContext.removeClientAttachment();
        RpcContext.getClientAttachment().setAttachment(key, value);
        RpcContext.getClientAttachment().setAttachment(key2, value2);
        GreeterReply reply = delegate.greetWithAttachment(
                GreeterRequest.newBuilder().setName("meta").build());
        Assert.assertEquals("hello,meta", reply.getMessage());
        validUpperHeader(key2, value2);
    }

    protected void validUpperHeader(String key2, String value2) {
        final String returned2 = (String) RpcContext.getServerContext().getObjectAttachment(key2);
        Assert.assertEquals(value2, returned2);
    }

    @Test
    public void attachmentTest2() {
        final String key = "user-attachment";
        final String value = "attachment-value";
        RpcContext.removeClientAttachment();
        RpcContext.getClientAttachment().setAttachment(key, value);
        GreeterReply reply = delegate.greetWithAttachment(
                GreeterRequest.newBuilder().setName("meta").build());
        Assert.assertEquals("hello,meta", reply.getMessage());
        final String returned = (String) RpcContext.getServerContext().getObjectAttachment(key);
        Assert.assertEquals("hello," + value, returned);
    }

    @AfterClass
    public static void alterTest() {
        appDubboBootstrap.destroy();
        DubboBootstrap.reset();
    }

}
