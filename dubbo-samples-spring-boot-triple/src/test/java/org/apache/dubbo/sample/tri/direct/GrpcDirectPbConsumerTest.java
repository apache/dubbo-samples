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
package org.apache.dubbo.sample.tri.direct;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.apache.dubbo.sample.tri.PbGreeterGrpc;
import org.apache.dubbo.sample.tri.TriApplication;
import org.apache.dubbo.sample.tri.common.IpUtils;
import org.apache.dubbo.sample.tri.common.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.common.TriSampleConstants;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GrpcDirectPbConsumerTest {

    private static PbGreeterGrpc.PbGreeterStub stub;
    private static PbGreeterGrpc.PbGreeterBlockingStub blockingStub;

    @BeforeClass
    public static void init() {
        if (IpUtils.checkIpPort(TriSampleConstants.HOST, TriSampleConstants.SERVER_PORT) == false) {
            new Thread(()->{
                TriApplication.main(new String[0]);
            }).start();
        }
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(TriSampleConstants.HOST, TriSampleConstants.SERVER_PORT)
                .usePlaintext()
                .build();
        stub = PbGreeterGrpc.newStub(channel);
        blockingStub = PbGreeterGrpc.newBlockingStub(channel);
    }

//    @Test
//    public void clientSendLargeSizeHeader() throws InterruptedException {
//        final Metadata.Key<String> key = Metadata.Key.of("large_size", Metadata.ASCII_STRING_MARSHALLER);
//        StringBuilder sb = new StringBuilder("a");
//        for (int j = 0; j < 15; j++) {
//            sb.append(sb);
//        }
//        Metadata meta = new Metadata();
//        meta.put(key, sb.toString());
//        final PbGreeterGrpc.PbGreeterStub curStub = MetadataUtils.attachHeaders(GrpcDirectPbConsumerTest.stub, meta);
//        curStub.greet(GreeterRequest.newBuilder().setName("metadata").build(), new StdoutStreamObserver<>("meta"));
//        TimeUnit.SECONDS.sleep(1);
//    }

    @Test
    public void serverStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        stub.greetServerStream(request, new StdoutStreamObserver<GreeterReply>("grpc sayGreeterServerStream") {
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

    @Disabled
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
        final PbGreeterGrpc.PbGreeterBlockingStub curStub = MetadataUtils.attachHeaders(GrpcDirectPbConsumerTest.blockingStub, meta);
        GreeterReply reply = curStub.greetWithAttachment(GreeterRequest.newBuilder().setName("meta").build());
        Assert.assertEquals("hello,meta", reply.getMessage());
    }

}

