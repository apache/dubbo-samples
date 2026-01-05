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
import org.apache.dubbo.rpc.protocol.tri.CancelableStreamObserver;
import org.apache.dubbo.rpc.protocol.tri.observer.ClientCallToObserverAdapter;
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
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration test for Triple protocol backpressure functionality.
 *
 * <p>This test covers all streaming scenarios with backpressure APIs:
 *
 * <h3>High-Level API (setOnReadyHandler) - Controlling Send Rate</h3>
 * <ul>
 *   <li>{@link #testServerStreamWithOnReadyHandler()} - Server uses isReady() + setOnReadyHandler() to control sending</li>
 *   <li>{@link #testClientStreamWithOnReadyHandler()} - Client uses isReady() + setOnReadyHandler() to control sending</li>
 *   <li>{@link #testBiStreamWithOnReadyHandler()} - Both sides use setOnReadyHandler()</li>
 * </ul>
 *
 * <h3>Low-Level API (disableAutoRequestWithInitial) - Controlling Receive Rate</h3>
 * <ul>
 *   <li>{@link #testServerStreamWithManualRequest()} - Client uses CancelableStreamObserver.beforeStart() to control receive rate</li>
 *   <li>{@link #testClientStreamWithDisableAutoRequestWithInitial()} - Client uses CancelableStreamObserver.beforeStart()</li>
 *   <li>{@link #testBiStreamWithDisableAutoRequestWithInitial()} - Client uses CancelableStreamObserver.beforeStart()</li>
 * </ul>
 */
public class BackpressureIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackpressureIT.class);

    // Use random port to avoid conflicts between consecutive test runs
    private static final int PORT = 50052 + (int)(Math.random() * 1000);

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

        // Start both provider and consumer using newInstance to avoid singleton pollution
        bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("backpressure-test"))
                .registry(new RegistryConfig("N/A"))  // No registry needed
                .protocol(new ProtocolConfig("tri", PORT))
                .service(serviceConfig)
                .reference(reference)
                .start();

        service = reference.get();
        LOGGER.info("Provider and Consumer started on port {}", PORT);

        // Warm up the connection to ensure resources are properly initialized
        // This prevents timing issues when running individual tests
        try {
            service.echo("warmup");
            LOGGER.info("Connection warmup completed");
        } catch (Exception e) {
            LOGGER.warn("Warmup failed, but continuing: {}", e.getMessage());
        }
    }

    @AfterClass
    public static void teardown() {
        if (bootstrap != null) {
            bootstrap.stop();
        }
    }

    // ==================== Basic Test ====================

    @Test
    public void testEcho() {
        String result = service.echo("Hello Backpressure");
        LOGGER.info("Echo response: {}", result);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("Hello Backpressure"));
    }

    // ==================== Server Stream Tests ====================

    /**
     * Test server streaming with HIGH-LEVEL API (setOnReadyHandler).
     * Server uses isReady() and setOnReadyHandler() to control sending rate.
     * This demonstrates SERVER-SIDE send backpressure.
     */
    @Test
    public void testServerStreamWithOnReadyHandler() throws InterruptedException {
        final int requestCount = 30;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger receivedCount = new AtomicInteger(0);

        StreamRequest request = new StreamRequest(requestCount, 1024);

        service.serverStream(request, new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk chunk) {
                int count = receivedCount.incrementAndGet();
                if (count % 10 == 0) {
                    LOGGER.info("[ServerStream-OnReady] Received {} chunks", count);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[ServerStream-OnReady] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[ServerStream-OnReady] Completed, received: {}", receivedCount.get());
                latch.countDown();
            }
        });

        boolean completed = latch.await(30, TimeUnit.SECONDS);
        Assert.assertTrue("Stream should complete within timeout", completed);
        Assert.assertEquals("Should receive all chunks", requestCount, receivedCount.get());
        LOGGER.info("✅ Server stream with onReadyHandler test passed!");
    }

    /**
     * Test server streaming with LOW-LEVEL API (disableAutoRequestWithInitial).
     * Client uses CancelableStreamObserver.beforeStart() to configure receive backpressure
     * BEFORE the stream starts, following gRPC's pattern.
     * This demonstrates CLIENT-SIDE receive backpressure.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testServerStreamWithManualRequest() throws InterruptedException {
        final int requestCount = 30;
        final int initialRequest = 5;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger receivedCount = new AtomicInteger(0);

        StreamRequest request = new StreamRequest(requestCount, 1024);

        // Use CancelableStreamObserver to configure receive backpressure BEFORE the stream starts
        CancelableStreamObserver<DataChunk> responseObserver = new CancelableStreamObserver<DataChunk>() {
            // Member variable to hold the adapter for manual request
            private ClientCallToObserverAdapter<DataChunk> adapter;

            @Override
            public void beforeStart(ClientCallToObserverAdapter<DataChunk> clientCallToObserverAdapter) {
                this.adapter = clientCallToObserverAdapter;
                // Configure receive backpressure - disable auto request and set initial request
                clientCallToObserverAdapter.disableAutoRequestWithInitial(initialRequest);
                LOGGER.info("[ServerStream-ManualRequest] beforeStart: configured receive backpressure, initialRequest={}",
                        initialRequest);
            }

            @Override
            public void onNext(DataChunk chunk) {
                int count = receivedCount.incrementAndGet();
                if (count % 5 == 0) {
                    LOGGER.info("[ServerStream-ManualRequest] Received {} chunks", count);
                }

                // After processing each chunk, request more data
                // This simulates controlled consumption rate
                if (adapter != null) {
                    adapter.request(1);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[ServerStream-ManualRequest] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[ServerStream-ManualRequest] Completed, received: {}", receivedCount.get());
                latch.countDown();
            }
        };

        // Start the stream - beforeStart() is called inside
        service.serverStream(request, responseObserver);

        boolean completed = latch.await(60, TimeUnit.SECONDS);
        Assert.assertTrue("Stream should complete within timeout", completed);
        Assert.assertEquals("Should receive all chunks", requestCount, receivedCount.get());
        LOGGER.info("✅ Server stream with manual request test passed!");
    }

    // ==================== Client Stream Tests ====================

    /**
     * Test client streaming with HIGH-LEVEL API (setOnReadyHandler).
     * Uses CancelableStreamObserver.beforeStart() to configure send backpressure
     * BEFORE the stream starts, following gRPC's pattern.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testClientStreamWithOnReadyHandler() throws InterruptedException {
        final int sendCount = 50;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger sent = new AtomicInteger(0);
        final AtomicBoolean sendCompleted = new AtomicBoolean(false);
        final AtomicInteger serverReceivedChunks = new AtomicInteger(0);
        final byte[] data = new byte[1024];

        // Use CancelableStreamObserver to configure backpressure BEFORE the stream starts
        CancelableStreamObserver<StreamResponse> responseObserver = new CancelableStreamObserver<StreamResponse>() {
            @Override
            public void beforeStart(ClientCallToObserverAdapter<StreamResponse> requestAdapter) {
                LOGGER.info("[ClientStream-OnReady] beforeStart called");

                // Disable auto flow control for manual send control
                requestAdapter.disableAutoFlowControl();

                // Set onReadyHandler BEFORE the stream starts
                requestAdapter.setOnReadyHandler(() -> {
                    LOGGER.info("[ClientStream-OnReady] onReadyHandler triggered, isReady={}, sent={}",
                            requestAdapter.isReady(), sent.get());
                    while (requestAdapter.isReady() && sent.get() < sendCount && !sendCompleted.get()) {
                        int seq = sent.getAndIncrement();
                        requestAdapter.onNext(new DataChunk(seq, data, System.currentTimeMillis()));
                    }

                    if (sent.get() >= sendCount && !sendCompleted.getAndSet(true)) {
                        requestAdapter.onCompleted();
                        LOGGER.info("[ClientStream-OnReady] onCompleted called");
                    }
                });
            }

            @Override
            public void onNext(StreamResponse response) {
                serverReceivedChunks.set(response.getTotalChunks());
                LOGGER.info("[ClientStream-OnReady] Server received: {} chunks", response.getTotalChunks());
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[ClientStream-OnReady] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[ClientStream-OnReady] Completed");
                latch.countDown();
            }
        };

        // Start the stream - beforeStart() is called inside
        StreamObserver<DataChunk> requestObserver = service.clientStream(responseObserver);

        // Note: We don't configure anything here because it's already done in beforeStart()

        boolean completed = latch.await(60, TimeUnit.SECONDS);
        Assert.assertTrue("Stream should complete within timeout", completed);
        Assert.assertEquals("Server should receive all chunks", sendCount, serverReceivedChunks.get());
        LOGGER.info("✅ Client stream with onReadyHandler test passed!");
    }

    /**
     * Test client streaming with LOW-LEVEL API (disableAutoRequestWithInitial).
     * Uses CancelableStreamObserver.beforeStart() to set up both send and receive backpressure
     * BEFORE the stream starts, following gRPC's pattern.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testClientStreamWithDisableAutoRequestWithInitial() throws InterruptedException {
        final int sendCount = 30;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger sent = new AtomicInteger(0);
        final AtomicInteger serverReceivedChunks = new AtomicInteger(0);
        final AtomicBoolean responseReceived = new AtomicBoolean(false);
        final byte[] data = new byte[1024];

        // Use CancelableStreamObserver to configure backpressure BEFORE the stream starts
        CancelableStreamObserver<StreamResponse> responseObserver = new CancelableStreamObserver<StreamResponse>() {
            @Override
            public void beforeStart(ClientCallToObserverAdapter<StreamResponse> clientCallToObserverAdapter) {
                LOGGER.info("[ClientStream-DisableAutoRequestWithInitial] beforeStart called");

                // Configure receive backpressure
                clientCallToObserverAdapter.disableAutoRequestWithInitial(10);

                // Configure send backpressure
                clientCallToObserverAdapter.disableAutoFlowControl();

                // Set onReadyHandler BEFORE the stream starts
                clientCallToObserverAdapter.setOnReadyHandler(() -> {
                    LOGGER.info("[ClientStream-DisableAutoRequestWithInitial] onReadyHandler triggered, isReady={}, sent={}",
                            clientCallToObserverAdapter.isReady(), sent.get());
                    while (clientCallToObserverAdapter.isReady() && sent.get() < sendCount) {
                        int seq = sent.getAndIncrement();
                        clientCallToObserverAdapter.onNext(new DataChunk(seq, data, System.currentTimeMillis()));
                    }

                    if (sent.get() >= sendCount) {
                        clientCallToObserverAdapter.onCompleted();
                        LOGGER.info("[ClientStream-DisableAutoRequestWithInitial] onCompleted called");
                    }
                });
            }

            @Override
            public void onNext(StreamResponse response) {
                serverReceivedChunks.set(response.getTotalChunks());
                responseReceived.set(true);
                LOGGER.info("[ClientStream-DisableAutoRequestWithInitial] Response: {} chunks",
                        response.getTotalChunks());
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[ClientStream-DisableAutoRequestWithInitial] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[ClientStream-DisableAutoRequestWithInitial] Completed");
                latch.countDown();
            }
        };

        // Start the stream - beforeStart() is called inside
        StreamObserver<DataChunk> requestObserver = service.clientStream(responseObserver);

        // Note: We don't configure anything here because it's already done in beforeStart()

        boolean completed = latch.await(60, TimeUnit.SECONDS);
        Assert.assertTrue("Stream should complete within timeout", completed);
        Assert.assertEquals("Server should receive all chunks", sendCount, serverReceivedChunks.get());
        Assert.assertTrue("Should receive response", responseReceived.get());
        LOGGER.info("✅ Client stream with disableAutoRequestWithInitial test passed!");
    }

    // ==================== Bidirectional Stream Tests ====================

    /**
     * Test bidirectional streaming with HIGH-LEVEL API (setOnReadyHandler).
     * Uses CancelableStreamObserver.beforeStart() to configure send backpressure
     * BEFORE the stream starts, following gRPC's pattern.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testBiStreamWithOnReadyHandler() throws InterruptedException {
        final int sendCount = 30;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger sent = new AtomicInteger(0);
        final AtomicInteger received = new AtomicInteger(0);
        final AtomicBoolean sendCompleted = new AtomicBoolean(false);
        final byte[] data = new byte[1024];

        // Use AtomicReference to access the request adapter from the onReadyHandler
        final AtomicReference<ClientCallToObserverAdapter<DataChunk>> requestAdapterRef = new AtomicReference<>();

        // Use CancelableStreamObserver to configure backpressure BEFORE the stream starts
        // This follows gRPC's pattern where setOnReadyHandler must be called in beforeStart()
        CancelableStreamObserver<DataChunk> responseObserver = new CancelableStreamObserver<DataChunk>() {
            @Override
            public void beforeStart(ClientCallToObserverAdapter<DataChunk> requestAdapter) {
                requestAdapterRef.set(requestAdapter);

                // Disable auto flow control for manual send control
                requestAdapter.disableAutoFlowControl();

                // Set onReadyHandler BEFORE the stream starts
                requestAdapter.setOnReadyHandler(() -> {
                    LOGGER.info("[BiStream-OnReady] onReadyHandler triggered, isReady={}, sent={}",
                            requestAdapter.isReady(), sent.get());
                    while (requestAdapter.isReady() && sent.get() < sendCount && !sendCompleted.get()) {
                        int seq = sent.getAndIncrement();
                        requestAdapter.onNext(new DataChunk(seq, data, System.currentTimeMillis()));
                    }

                    if (sent.get() >= sendCount && !sendCompleted.getAndSet(true)) {
                        requestAdapter.onCompleted();
                        LOGGER.info("[BiStream-OnReady] onCompleted called");
                    }
                });
                LOGGER.info("[BiStream-OnReady] beforeStart: configured send backpressure");
            }

            @Override
            public void onNext(DataChunk chunk) {
                int count = received.incrementAndGet();
                if (count % 10 == 0) {
                    LOGGER.info("[BiStream-OnReady] Received {} response chunks", count);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[BiStream-OnReady] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[BiStream-OnReady] Completed, sent: {}, received: {}",
                        sent.get(), received.get());
                latch.countDown();
            }
        };

        // Start the stream - beforeStart() is called inside
        StreamObserver<DataChunk> requestObserver = service.biStream(responseObserver);

        // Note: We don't need to set onReadyHandler here because it's already set in beforeStart()
        // The onReadyHandler will be triggered by the framework when the stream becomes ready

        boolean completed = latch.await(60, TimeUnit.SECONDS);
        Assert.assertTrue("BiStream should complete within timeout", completed);
        Assert.assertEquals("Should send all chunks", sendCount, sent.get());
        Assert.assertTrue("Should receive response chunks", received.get() > 0);
        LOGGER.info("✅ BiStream with onReadyHandler test passed! sent={}, received={}",
                sent.get(), received.get());
    }

    /**
     * Test bidirectional streaming with LOW-LEVEL API (disableAutoRequestWithInitial).
     * Uses CancelableStreamObserver.beforeStart() to set up BOTH send and receive backpressure
     * BEFORE the stream starts, following gRPC's pattern.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testBiStreamWithDisableAutoRequestWithInitial() throws InterruptedException {
        final int sendCount = 30;
        final int initialRequest = 10;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger sent = new AtomicInteger(0);
        final AtomicInteger received = new AtomicInteger(0);
        final AtomicBoolean sendCompleted = new AtomicBoolean(false);
        final byte[] data = new byte[1024];

        // Use CancelableStreamObserver to configure BOTH send and receive backpressure BEFORE the stream starts
        CancelableStreamObserver<DataChunk> responseObserver = new CancelableStreamObserver<DataChunk>() {
            // Member variable to hold the request adapter - same pattern as gRPC
            private ClientCallToObserverAdapter<DataChunk> requestAdapter;

            @Override
            public void beforeStart(ClientCallToObserverAdapter<DataChunk> clientCallToObserverAdapter) {
                this.requestAdapter = clientCallToObserverAdapter;

                // Configure receive backpressure (LOW-LEVEL API)
                clientCallToObserverAdapter.disableAutoRequestWithInitial(initialRequest);

                // Configure send backpressure (HIGH-LEVEL API)
                clientCallToObserverAdapter.disableAutoFlowControl();

                // Set onReadyHandler BEFORE the stream starts (following gRPC pattern)
                clientCallToObserverAdapter.setOnReadyHandler(() -> {
                    LOGGER.info("[BiStream-DisableAutoRequestWithInitial] onReadyHandler triggered, isReady={}, sent={}",
                            clientCallToObserverAdapter.isReady(), sent.get());
                    while (clientCallToObserverAdapter.isReady() && sent.get() < sendCount && !sendCompleted.get()) {
                        int seq = sent.getAndIncrement();
                        clientCallToObserverAdapter.onNext(new DataChunk(seq, data, System.currentTimeMillis()));
                    }

                    if (sent.get() >= sendCount && !sendCompleted.getAndSet(true)) {
                        clientCallToObserverAdapter.onCompleted();
                        LOGGER.info("[BiStream-DisableAutoRequestWithInitial] onCompleted called");
                    }
                });

                LOGGER.info("[BiStream-DisableAutoRequestWithInitial] beforeStart: configured both send and receive backpressure");
            }

            @Override
            public void onNext(DataChunk chunk) {
                int count = received.incrementAndGet();
                if (count % 10 == 0) {
                    LOGGER.info("[BiStream-DisableAutoRequestWithInitial] Received {} chunks", count);
                }

                // Request more after processing - using member variable directly
                if (requestAdapter != null) {
                    requestAdapter.request(1);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[BiStream-DisableAutoRequestWithInitial] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[BiStream-DisableAutoRequestWithInitial] Completed, sent: {}, received: {}",
                        sent.get(), received.get());
                latch.countDown();
            }
        };

        // Start the stream - beforeStart() is called inside, which sets up all backpressure configuration
        StreamObserver<DataChunk> requestObserver = service.biStream(responseObserver);

        // Note: We don't configure anything here because it's already done in beforeStart()

        boolean completed = latch.await(60, TimeUnit.SECONDS);
        Assert.assertTrue("BiStream should complete within timeout", completed);
        Assert.assertEquals("Should send all chunks", sendCount, sent.get());
        LOGGER.info("✅ BiStream with disableAutoRequestWithInitial test passed! sent={}, received={}",
                sent.get(), received.get());
    }
}
