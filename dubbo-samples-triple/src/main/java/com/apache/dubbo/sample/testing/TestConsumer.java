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

package com.apache.dubbo.sample.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import com.google.common.util.concurrent.SettableFuture;
import com.google.protobuf.ByteString;
import io.grpc.testing.integration.EmptyProtos.Empty;
import io.grpc.testing.integration.Messages.Payload;
import io.grpc.testing.integration.Messages.SimpleRequest;
import io.grpc.testing.integration.Messages.SimpleResponse;
import io.grpc.testing.integration.Messages.StreamingInputCallRequest;
import io.grpc.testing.integration.Messages.StreamingInputCallResponse;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.status.Status;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.junit.Assert;
import org.junit.Assume;

import static org.junit.Assert.assertEquals;

public class TestConsumer {
    protected static final Empty EMPTY = Empty.getDefaultInstance();
    public static void main(String[] args) throws Exception {
        ReferenceConfig<ITestService> ref = new ReferenceConfig<>();
        ref.setInterface(ITestService.class);
        ref.setCheck(false);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);
        ref.setTimeout(100000);
        ref.setApplication(new ApplicationConfig("demo-consumer"));
        ref.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        final ITestService testService = ref.get();

        System.out.println("dubbo ref started");
        Assert.assertEquals(EMPTY, testService.emptyCall(EMPTY));

        assumeEnoughMemory();
        final SimpleRequest unaryRequest = SimpleRequest.newBuilder()
            .setResponseSize(314159)
            .setPayload(Payload.newBuilder()
                .setBody(ByteString.copyFrom(new byte[271828])))
            .build();
        final SimpleResponse goldenUnaryResponse = SimpleResponse.newBuilder()
            .setPayload(Payload.newBuilder()
                .setBody(ByteString.copyFrom(new byte[314159])))
            .build();

        assertResponse(goldenUnaryResponse, testService.unaryCall(unaryRequest));

        final List<StreamingInputCallRequest> requests = Arrays.asList(
            StreamingInputCallRequest.newBuilder()
                .setPayload(Payload.newBuilder()
                    .setBody(ByteString.copyFrom(new byte[27182])))
                .build(),
            StreamingInputCallRequest.newBuilder()
                .setPayload(Payload.newBuilder()
                    .setBody(ByteString.copyFrom(new byte[8])))
                .build(),
            StreamingInputCallRequest.newBuilder()
                .setPayload(Payload.newBuilder()
                    .setBody(ByteString.copyFrom(new byte[1828])))
                .build(),
            StreamingInputCallRequest.newBuilder()
                .setPayload(Payload.newBuilder()
                    .setBody(ByteString.copyFrom(new byte[45904])))
                .build());

        final StreamingInputCallResponse goldenStreamingInputCallResponse = StreamingInputCallResponse.newBuilder()
            .setAggregatedPayloadSize(74922)
            .build();

        StreamingInputCallStreamObserver<StreamingInputCallResponse> responseObserver = new StreamingInputCallStreamObserver();

        StreamObserver<StreamingInputCallRequest> requestObserver = testService.streamingInputCall(responseObserver);

        for (StreamingInputCallRequest request : requests) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();

        assertEquals(goldenStreamingInputCallResponse, responseObserver.getFirstValue().get());
        responseObserver.awaitCompletion();
        Assert.assertEquals(responseObserver.getResults().size(), 1);
        Throwable t = responseObserver.getError();
        if (t != null) {
            throw new AssertionError(t);
        }

        System.in.read();
    }

    static class  StreamingInputCallStreamObserver<T> implements StreamObserver<T> {

        public StreamingInputCallStreamObserver() {
        }

        private final CountDownLatch latch = new CountDownLatch(1);
        private final List<T> results = Collections.synchronizedList(new ArrayList<T>());
        private Throwable error;
        private final SettableFuture<T> firstValue = SettableFuture.create();

        @Override
        public void onNext(T data) {
            if (!firstValue.isDone()) {
                firstValue.set(data);
            }
            results.add(data);
        }

        @Override
        public void onError(Throwable throwable) {
            if (!firstValue.isDone()) {
                firstValue.setException(throwable);
            }
            error = throwable;
            System.out.println("onError" + throwable);
            latch.countDown();
        }

        @Override
        public void onCompleted() {
            if (!firstValue.isDone()) {
                firstValue.setException(new IllegalStateException("No first value provided"));
            }
            System.out.println("onCompleted");
            latch.countDown();
        }

        public void awaitCompletion() throws Exception {
            latch.await();
        }

        public List getResults() {
            return results;
        }

        public Throwable getError() {
            return error;
        }

        public SettableFuture getFirstValue() {
            return firstValue;
        }
    }


    /**
     * Some tests run on memory constrained environments.  Rather than OOM, just give up.  64 is
     * chosen as a maximum amount of memory a large test would need.
     */
    protected static void assumeEnoughMemory() {
        Runtime r = Runtime.getRuntime();
        long usedMem = r.totalMemory() - r.freeMemory();
        long actuallyFreeMemory = r.maxMemory() - usedMem;
        Assume.assumeTrue(
            actuallyFreeMemory + " is not sufficient to run this test",
            actuallyFreeMemory >= 64 * 1024 * 1024);
    }

    protected static void assertResponse(SimpleResponse expected, SimpleResponse actual) {
        assertPayload(expected.getPayload(), actual.getPayload());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getOauthScope(), actual.getOauthScope());
    }

    private static void assertPayload(Payload expected, Payload actual) {
        // Compare non deprecated fields in Payload, to make this test forward compatible.
        if (expected == null || actual == null) {
            assertEquals(expected, actual);
        } else {
            assertEquals(expected.getBody(), actual.getBody());
        }
    }
}
