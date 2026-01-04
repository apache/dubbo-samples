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
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.protocol.tri.observer.CallStreamObserver;
import org.apache.dubbo.samples.backpressure.api.BackpressureService;
import org.apache.dubbo.samples.backpressure.api.DataChunk;
import org.apache.dubbo.samples.backpressure.api.StreamRequest;
import org.apache.dubbo.samples.backpressure.api.StreamResponse;
import org.apache.dubbo.samples.backpressure.impl.BackpressureServiceImpl;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration test for backpressure functionality.
 * This test starts both provider and consumer in the same JVM using direct connection (no registry needed).
 */
public class BackpressureIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackpressureIT.class);

    private static final int PORT = 50052;

    private static BackpressureService service;
    private static DubboBootstrap bootstrap;

    @BeforeClass
    public static void setup() {
        // Provider config
        ServiceConfig<BackpressureService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(BackpressureService.class);
        serviceConfig.setRef(new BackpressureServiceImpl());

        // Consumer config with direct connection (no registry needed)
        ReferenceConfig<BackpressureService> reference = new ReferenceConfig<>();
        reference.setInterface(BackpressureService.class);
        reference.setUrl("tri://127.0.0.1:" + PORT);
        reference.setTimeout(60000);

        // Start both provider and consumer
        bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("backpressure-test"))
                .registry(new RegistryConfig("N/A"))  // No registry needed
                .protocol(new ProtocolConfig("tri", PORT))
                .service(serviceConfig)
                .reference(reference)
                .start();

        service = reference.get();
        LOGGER.info("Provider and Consumer started on port {}", PORT);
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

    /**
     * Test server-side backpressure.
     * Server uses isReady() and setOnReadyHandler() to control sending rate.
     */
    @Test
    public void testServerSideBackpressure() throws InterruptedException {
        final int requestCount = 30;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger receivedCount = new AtomicInteger(0);

        StreamRequest request = new StreamRequest(requestCount, 1024);

        service.serverStream(request, new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk chunk) {
                receivedCount.incrementAndGet();
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("Server stream completed, received: {}", receivedCount.get());
                latch.countDown();
            }
        });

        boolean completed = latch.await(30, TimeUnit.SECONDS);
        Assert.assertTrue("Stream should complete within timeout", completed);
        Assert.assertEquals("Should receive all chunks", requestCount, receivedCount.get());
        LOGGER.info("✅ Server-side backpressure test passed!");
    }

    /**
     * Test client-side backpressure.
     * Client uses CallStreamObserver's isReady() and setOnReadyHandler() to control sending rate.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testClientSideBackpressure() throws InterruptedException {
        final int sendCount = 50;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger sent = new AtomicInteger(0);
        final AtomicBoolean sendCompleted = new AtomicBoolean(false);
        final AtomicInteger serverReceivedChunks = new AtomicInteger(0);
        final byte[] data = new byte[1024];

        StreamObserver<StreamResponse> responseObserver = new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse response) {
                serverReceivedChunks.set(response.getTotalChunks());
                LOGGER.info("Server received: {} chunks", response.getTotalChunks());
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("Client stream completed");
                latch.countDown();
            }
        };

        StreamObserver<DataChunk> requestObserver = service.clientStream(responseObserver);

        // Cast to CallStreamObserver to use backpressure API
        CallStreamObserver<DataChunk> callObserver = (CallStreamObserver<DataChunk>) requestObserver;

        // Disable auto flow control
        callObserver.disableAutoFlowControl();

        // Set ready callback
        callObserver.setOnReadyHandler(() -> {
            while (callObserver.isReady() && sent.get() < sendCount && !sendCompleted.get()) {
                int seq = sent.getAndIncrement();
                callObserver.onNext(new DataChunk(seq, data, System.currentTimeMillis()));
            }

            if (sent.get() >= sendCount && !sendCompleted.getAndSet(true)) {
                callObserver.onCompleted();
            }
        });

        // Initial send
        while (callObserver.isReady() && sent.get() < sendCount) {
            int seq = sent.getAndIncrement();
            callObserver.onNext(new DataChunk(seq, data, System.currentTimeMillis()));
        }

        if (sent.get() >= sendCount && !sendCompleted.getAndSet(true)) {
            callObserver.onCompleted();
        }

        boolean completed = latch.await(60, TimeUnit.SECONDS);
        Assert.assertTrue("Stream should complete within timeout", completed);
        Assert.assertEquals("Server should receive all chunks", sendCount, serverReceivedChunks.get());
        LOGGER.info("✅ Client-side backpressure test passed!");
    }
}
