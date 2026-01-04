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
import org.apache.dubbo.rpc.protocol.tri.observer.ClientCallToObserverAdapter;
import org.apache.dubbo.samples.backpressure.api.BackpressureService;
import org.apache.dubbo.samples.backpressure.api.DataChunk;
import org.apache.dubbo.samples.backpressure.api.StreamRequest;
import org.apache.dubbo.samples.backpressure.api.StreamResponse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackpressureConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackpressureConsumer.class);

    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");

    public static void main(String[] args) throws Exception {
        ReferenceConfig<BackpressureService> reference = new ReferenceConfig<>();
        reference.setInterface(BackpressureService.class);
        reference.setProtocol("tri");

        String zkAddress = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;
        LOGGER.info("Using ZooKeeper: {}", zkAddress);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("backpressure-consumer"))
                .registry(new RegistryConfig(zkAddress))
                .reference(reference)
                .start();

        BackpressureService service = reference.get();

        LOGGER.info("=== Test 1: Basic Echo ===");
        testEcho(service);

        LOGGER.info("\n=== Test 2: Client-side Backpressure (clientStream) ===");
        testClientSideBackpressure(service);

        LOGGER.info("\n=== Test 3: Server-side Backpressure (serverStream) ===");
        testServerSideBackpressure(service);

        LOGGER.info("\n✅ All tests completed!");
        System.exit(0);
    }

    /**
     * Test basic echo functionality.
     */
    public static void testEcho(BackpressureService service) {
        String response = service.echo("Hello Backpressure");
        LOGGER.info("Echo response: {}", response);
    }

    /**
     * Test server-side backpressure.
     * Server uses isReady() and setOnReadyHandler() to control sending rate.
     */
    public static void testServerSideBackpressure(BackpressureService service) throws InterruptedException {
        final int expectedCount = 50;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger received = new AtomicInteger(0);

        StreamRequest request = new StreamRequest(expectedCount, 1024);

        LOGGER.info("[Server-Backpressure] Requesting {} chunks from server", expectedCount);

        service.serverStream(request, new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk chunk) {
                int count = received.incrementAndGet();
                if (count % 10 == 0) {
                    LOGGER.info("[Server-Backpressure] Received {} chunks", count);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[Server-Backpressure] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[Server-Backpressure] Stream completed, total: {}", received.get());
                latch.countDown();
            }
        });

        latch.await(60, TimeUnit.SECONDS);
        LOGGER.info("[Server-Backpressure] Test finished, received: {}", received.get());
    }

    /**
     * Test client-side backpressure.
     *
     * <p>Client uses CallStreamObserver's isReady() and setOnReadyHandler()
     * to control sending rate based on transport layer capacity.
     */
    public static void testClientSideBackpressure(BackpressureService service) throws InterruptedException {
        final int sendCount = 100;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger sent = new AtomicInteger(0);
        final AtomicBoolean completed = new AtomicBoolean(false);
        final byte[] data = new byte[1024];

        LOGGER.info("[Client-Backpressure] Sending {} chunks to server using backpressure", sendCount);

        // Response observer
        StreamObserver<StreamResponse> responseObserver = new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse response) {
                LOGGER.info("[Client-Backpressure] Server received: {} chunks, {} bytes in {}ms",
                        response.getTotalChunks(), response.getTotalBytes(), response.getDurationMs());
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[Client-Backpressure] Error: {}", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[Client-Backpressure] Response completed");
                latch.countDown();
            }
        };

        // Get request StreamObserver
        StreamObserver<DataChunk> requestObserver = service.clientStream(responseObserver);

        // Cast to ClientCallToObserverAdapter to use backpressure API
        ClientCallToObserverAdapter<DataChunk> callObserver = (ClientCallToObserverAdapter<DataChunk>) requestObserver;

        // Disable auto flow control for manual backpressure
        callObserver.disableAutoFlowControl();
        LOGGER.info("[Client-Backpressure] Disabled auto flow control");

        // Set ready callback - called when stream becomes writable
        callObserver.setOnReadyHandler(() -> {
            LOGGER.info("[Client-Backpressure] onReadyHandler triggered, sent so far: {}", sent.get());

            // Send data while stream is ready and we have more to send
            while (callObserver.isReady() && sent.get() < sendCount && !completed.get()) {
                int seq = sent.getAndIncrement();
                DataChunk chunk = new DataChunk(seq, data, System.currentTimeMillis());
                callObserver.onNext(chunk);

                if ((seq + 1) % 20 == 0) {
                    LOGGER.info("[Client-Backpressure] Sent {} chunks (isReady={})", seq + 1, callObserver.isReady());
                }
            }

            // Complete if all data sent
            if (sent.get() >= sendCount && !completed.getAndSet(true)) {
                callObserver.onCompleted();
                LOGGER.info("[Client-Backpressure] Completed via onReadyHandler, total: {}", sent.get());
            }
        });

        // Initial send (if writable)
        LOGGER.info("[Client-Backpressure] Initial send, isReady={}", callObserver.isReady());
        while (callObserver.isReady() && sent.get() < sendCount) {
            int seq = sent.getAndIncrement();
            DataChunk chunk = new DataChunk(seq, data, System.currentTimeMillis());
            callObserver.onNext(chunk);

            if ((seq + 1) % 20 == 0) {
                LOGGER.info("[Client-Backpressure] Initial sent {} chunks", seq + 1);
            }
        }

        // Complete if all sent in initial phase
        if (sent.get() >= sendCount && !completed.getAndSet(true)) {
            callObserver.onCompleted();
            LOGGER.info("[Client-Backpressure] All sent in initial phase, total: {}", sent.get());
        } else {
            LOGGER.info("[Client-Backpressure] Paused at {} chunks, waiting for onReadyHandler", sent.get());
        }

        latch.await(120, TimeUnit.SECONDS);
        LOGGER.info("[Client-Backpressure] Test finished, total sent: {}", sent.get());
    }
}
