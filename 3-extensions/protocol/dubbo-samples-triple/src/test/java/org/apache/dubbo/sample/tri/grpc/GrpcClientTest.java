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

package org.apache.dubbo.sample.tri.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.apache.dubbo.sample.tri.GreeterGrpc;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.apache.dubbo.sample.tri.util.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GrpcClientTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcClientTest.class);
    private static GreeterGrpc.GreeterStub stub;
    private static GreeterGrpc.GreeterBlockingStub blockingStub;

    @BeforeClass
    public static void init() {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(TriSampleConstants.HOST, TriSampleConstants.GRPC_SERVER_PORT)
                .usePlaintext()
                .build();
        stub = GreeterGrpc.newStub(channel);
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    @Test
    public void clientSendLargeSizeHeader() throws InterruptedException {
        final Metadata.Key<String> key = Metadata.Key.of("large_size", Metadata.ASCII_STRING_MARSHALLER);
        StringBuilder sb = new StringBuilder("a");
        for (int j = 0; j < 15; j++) {
            sb.append(sb);
        }
        Metadata meta = new Metadata();
        meta.put(key, sb.toString());
        final GreeterGrpc.GreeterStub curStub = GrpcClientTest.stub
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(meta));
        curStub.greet(GreeterRequest.newBuilder().setName("metadata").build(), new StdoutStreamObserver<>("meta"));
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void serverStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        String key = "lastTimestamp";
        Map<String, Long> map = new HashMap<>();
        StdoutStreamObserver<GreeterReply> observer = new StdoutStreamObserver<GreeterReply>(
                "sayGreeterServerStream") {
            @Override
            public void onNext(GreeterReply data) {
                long now = System.currentTimeMillis();
                long lastTimestamp = map.getOrDefault(key, 0L);
                map.put(key, now);
                latch.countDown();
                if (lastTimestamp == 0) {
                    LOGGER.info("onNext now:{}", now);
                } else {
                    long diff = Math.abs(now - lastTimestamp - 1000);
                    if (diff < 50) {
                        LOGGER.info("onNext now:{} diff:{}", now, diff);
                    } else {
                        LOGGER.warn("onNext now:{} diff:{} >= 50!", now, diff);
                    }
                }
                super.onNext(data);
            }
        };
        stub.greetServerStream(request, observer);
        LOGGER.info("serverStream begin latch.await now:{}", System.currentTimeMillis());
        Assert.assertTrue(latch.await(12, TimeUnit.SECONDS));
    }



    @Test
    public void stream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        final io.grpc.stub.StreamObserver<GreeterRequest> requestObserver = stub.greetStream(new StdoutStreamObserver<GreeterReply>("sayGreeterStream") {
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
        final GreeterReply reply = blockingStub.greet(GreeterRequest.newBuilder()
                .setName("name")
                .build());
        Assert.assertNotNull(reply);
    }


    @Test
    public void attachmentTest() {
        final Metadata.Key<String> key = Metadata.Key.of("large_size", Metadata.ASCII_STRING_MARSHALLER);
        Metadata meta = new Metadata();
        meta.put(key, "test");
        final GreeterGrpc.GreeterBlockingStub curStub = GrpcClientTest.blockingStub
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(meta));
        GreeterReply reply = curStub.greetWithAttachment(GreeterRequest.newBuilder().setName("meta").build());
        Assert.assertEquals("hello,meta", reply.getMessage());
    }

}

