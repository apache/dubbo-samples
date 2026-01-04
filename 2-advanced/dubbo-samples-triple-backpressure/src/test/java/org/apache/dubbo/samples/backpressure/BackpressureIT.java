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
package org.apache.dubbo.samples.backpressure;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.protocol.tri.CancelableStreamObserver;
import org.apache.dubbo.rpc.protocol.tri.observer.ClientCallToObserverAdapter;
import org.apache.dubbo.samples.backpressure.api.BackpressureService;
import org.apache.dubbo.samples.backpressure.api.DataChunk;
import org.apache.dubbo.samples.backpressure.api.StreamRequest;
import org.apache.dubbo.samples.backpressure.api.StreamResponse;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackpressureIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackpressureIT.class);

    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");

    private static BackpressureService service;
    private static DubboBootstrap bootstrap;

    @BeforeClass
    public static void setup() {
        ReferenceConfig<BackpressureService> reference = new ReferenceConfig<>();
        reference.setInterface(BackpressureService.class);
        reference.setProtocol("tri");
        reference.setTimeout(60000);

        String zkAddress = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;
        LOGGER.info("Using ZooKeeper: {}", zkAddress);

        bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("backpressure-test"))
                .registry(new RegistryConfig(zkAddress))
                .reference(reference)
                .start();

        service = reference.get();
    }

    @AfterClass
    public static void teardown() {
        if (bootstrap != null) {
            bootstrap.stop();
        }
    }

    @Test
    public void testEcho() {
        String result = service.echo("Hello Backpressure");
        LOGGER.info("Echo response: {}", result);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("Hello Backpressure"));
    }

    @Test
    public void testServerStreamWithBackpressure() throws InterruptedException {
        final int requestCount = 20;
        final int batchSize = 5;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger receivedCount = new AtomicInteger(0);
        final AtomicInteger batchCount = new AtomicInteger(0);

        StreamRequest request = new StreamRequest(requestCount, 1024);

        CancelableStreamObserver<DataChunk> responseObserver = new CancelableStreamObserver<DataChunk>() {
            private ClientCallToObserverAdapter<DataChunk> clientObserver;

            @Override
            public void beforeStart(ClientCallToObserverAdapter<DataChunk> clientCallToObserverAdapter) {
                this.clientObserver = clientCallToObserverAdapter;
                LOGGER.info("[Backpressure] beforeStart() - Calling disableAutoFlowControl()");
                clientObserver.disableAutoFlowControl();
            }

            @Override
            public void startRequest() {
                LOGGER.info("[Backpressure] startRequest() - Calling request({})", batchSize);
                if (clientObserver != null) {
                    clientObserver.request(batchSize);
                }
            }

            @Override
            public void onNext(DataChunk chunk) {
                int count = receivedCount.incrementAndGet();
                LOGGER.info("[Backpressure] Received chunk seq={}, total={}", chunk.getSequenceNumber(), count);

                if (count % batchSize == 0 && clientObserver != null) {
                    int batch = batchCount.incrementAndGet();
                    LOGGER.info("[Backpressure] >>> Requesting next batch #{}", batch);
                    clientObserver.request(batchSize);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[Backpressure] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[Backpressure] Stream completed, received {} chunks", receivedCount.get());
                latch.countDown();
            }
        };

        service.serverStream(request, responseObserver);
        boolean completed = latch.await(30, TimeUnit.SECONDS);

        Assert.assertTrue("Stream should complete within timeout", completed);
        Assert.assertEquals("Should receive all chunks", requestCount, receivedCount.get());
        LOGGER.info("✅ Server stream with backpressure test passed!");
    }

    @Test
    public void testClientStream() throws InterruptedException {
        final int sendCount = 20;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger responseChunks = new AtomicInteger(0);

        StreamObserver<StreamResponse> responseObserver = new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse response) {
                responseChunks.set(response.getTotalChunks());
                LOGGER.info("[ClientStream] Received response: chunks={}", response.getTotalChunks());
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[ClientStream] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[ClientStream] Stream completed");
                latch.countDown();
            }
        };

        StreamObserver<DataChunk> requestObserver = service.clientStream(responseObserver);

        byte[] data = new byte[1024];
        for (int i = 0; i < sendCount; i++) {
            DataChunk chunk = new DataChunk(i, data, System.currentTimeMillis());
            requestObserver.onNext(chunk);
        }
        requestObserver.onCompleted();

        boolean completed = latch.await(60, TimeUnit.SECONDS);

        Assert.assertTrue("Stream should complete within timeout", completed);
        Assert.assertEquals("Server should receive all chunks", sendCount, responseChunks.get());
        LOGGER.info("✅ Client stream test passed!");
    }
}
