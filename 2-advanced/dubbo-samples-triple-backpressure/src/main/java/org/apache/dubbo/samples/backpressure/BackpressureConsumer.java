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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BackpressureConsumer {

    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");

    public static void main(String[] args) throws Exception {
        ReferenceConfig<BackpressureService> reference = new ReferenceConfig<>();
        reference.setInterface(BackpressureService.class);
        reference.setProtocol("tri");

        String zkAddress = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;
        System.out.println("Using ZooKeeper: " + zkAddress);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("backpressure-consumer"))
                .registry(new RegistryConfig(zkAddress))
                .reference(reference)
                .start();

        BackpressureService service = reference.get();

        System.out.println("=== Test 1: Basic Echo ===");
        testEcho(service);

        System.out.println("\n=== Test 2: Server Stream WITHOUT Backpressure ===");
        testServerStreamWithoutBackpressure(service);

        System.out.println("\n=== Test 3: Server Stream WITH Backpressure (CancelableStreamObserver) ===");
        testServerStreamWithBackpressure(service);

        System.out.println("\n=== Test 4: Client Stream ===");
        testClientStream(service);

        System.out.println("\n=== Test 5: BiStream WITH Backpressure ===");
        testBiStreamWithBackpressure(service);

        System.out.println("\n✅ All tests completed!");
        System.exit(0);
    }

    /**
     * Test basic echo functionality to ensure existing features work.
     */
    public static void testEcho(BackpressureService service) {
        String response = service.echo("Hello Backpressure");
        System.out.println("Echo response: " + response);
    }

    /**
     * Test server stream without backpressure (default behavior).
     */
    public static void testServerStreamWithoutBackpressure(BackpressureService service) throws InterruptedException {
        final int expectedCount = 100;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger received = new AtomicInteger(0);
        final AtomicLong firstTimestamp = new AtomicLong(0);
        final AtomicLong lastTimestamp = new AtomicLong(0);

        StreamRequest request = new StreamRequest(expectedCount, 1024);
        service.serverStream(request, new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk chunk) {
                int count = received.incrementAndGet();
                if (count == 1) {
                    firstTimestamp.set(System.currentTimeMillis());
                }
                lastTimestamp.set(System.currentTimeMillis());

                if (count % 20 == 0) {
                    System.out.println("[NoBackpressure] Received " + count + " chunks");
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("[NoBackpressure] Stream completed");
                latch.countDown();
            }
        });

        latch.await(60, TimeUnit.SECONDS);
        long duration = lastTimestamp.get() - firstTimestamp.get();
        System.out.println("[NoBackpressure] Total received: " + received.get() + ", Duration: " + duration + "ms");
    }

    /**
     * Test server stream WITH backpressure using CancelableStreamObserver.
     *
     * KEY: Must use CancelableStreamObserver to get ClientCallToObserverAdapter
     * via beforeStart() callback, which provides disableAutoFlowControl() and request(n).
     */
    public static void testServerStreamWithBackpressure(BackpressureService service) throws InterruptedException {
        final int expectedCount = 50;
        final int batchSize = 5;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger received = new AtomicInteger(0);
        final AtomicInteger batchCount = new AtomicInteger(0);
        final AtomicLong firstTimestamp = new AtomicLong(0);
        final AtomicLong lastTimestamp = new AtomicLong(0);

        StreamRequest request = new StreamRequest(expectedCount, 512);

        CancelableStreamObserver<DataChunk> responseObserver = new CancelableStreamObserver<DataChunk>() {

            private ClientCallToObserverAdapter<DataChunk> clientObserver;

            /**
             * Called BEFORE stream is established - only save reference and disable auto flow
             */
            @Override
            public void beforeStart(ClientCallToObserverAdapter<DataChunk> clientCallToObserverAdapter) {
                this.clientObserver = clientCallToObserverAdapter;

                System.out.println("[Backpressure] beforeStart() - Calling disableAutoFlowControl()");
                clientObserver.disableAutoFlowControl();
            }

            @Override
            public void startRequest() {
                System.out.println("[Backpressure] startRequest() called - stream is ready, requesting initial batch");
                if (clientObserver != null) {
                    System.out.println("[Backpressure] startRequest() - Calling request(" + batchSize + ")");
                    clientObserver.request(batchSize);
                }
            }

            @Override
            public void onNext(DataChunk chunk) {
                int count = received.incrementAndGet();
                if (count == 1) {
                    firstTimestamp.set(System.currentTimeMillis());
                }
                lastTimestamp.set(System.currentTimeMillis());

                // Simulate processing
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("[Backpressure] Received chunk seq=" + chunk.getSequenceNumber() + ", total=" + count);

                if (count % batchSize == 0 && clientObserver != null) {
                    int batch = batchCount.incrementAndGet();
                    System.out.println("[Backpressure] >>> Requesting next batch #" + batch + " (request " + batchSize + " more)");
                    clientObserver.request(batchSize);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
                throwable.printStackTrace();
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("[Backpressure] Stream completed");
                latch.countDown();
            }
        };

        // Start the server stream call
        service.serverStream(request, responseObserver);

        latch.await(120, TimeUnit.SECONDS);
        long duration = lastTimestamp.get() - firstTimestamp.get();
        System.out.println("[Backpressure] Total received: " + received.get() + ", Duration: " + duration + "ms");
        System.out.println("[Backpressure] Total batches requested: " + batchCount.get());
    }

    /**
     * Test client stream - demonstrates SERVER-SIDE backpressure.
     */
    public static void testClientStream(BackpressureService service) throws InterruptedException {
        final int sendCount = 50;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger sent = new AtomicInteger(0);

        StreamObserver<StreamResponse> responseObserver = new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse response) {
                System.out.println("[ClientStream] Received response: chunks=" + response.getTotalChunks() +
                        ", bytes=" + response.getTotalBytes() + ", duration=" + response.getDurationMs() + "ms");
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("[ClientStream] Error: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("[ClientStream] Stream completed");
                latch.countDown();
            }
        };

        StreamObserver<DataChunk> requestObserver = service.clientStream(responseObserver);

        byte[] data = new byte[1024];
        for (int i = 0; i < sendCount; i++) {
            DataChunk chunk = new DataChunk(i, data, System.currentTimeMillis());
            System.out.println("[ClientStream] Sending chunk seq=" + i);
            requestObserver.onNext(chunk);
            sent.incrementAndGet();
            Thread.sleep(10);
        }
        requestObserver.onCompleted();
        System.out.println("[ClientStream] Client finished sending " + sent.get() + " chunks");

        latch.await(60, TimeUnit.SECONDS);
    }

    /**
     * Test bidirectional stream with backpressure using CancelableStreamObserver.
     */
    public static void testBiStreamWithBackpressure(BackpressureService service) throws InterruptedException {
        final int sendCount = 20;
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicInteger received = new AtomicInteger(0);
        final AtomicInteger batchCount = new AtomicInteger(0);

        CancelableStreamObserver<DataChunk> responseObserver = new CancelableStreamObserver<DataChunk>() {

            private ClientCallToObserverAdapter<DataChunk> clientObserver;

            @Override
            public void beforeStart(ClientCallToObserverAdapter<DataChunk> clientCallToObserverAdapter) {
                this.clientObserver = clientCallToObserverAdapter;

                System.out.println("[BiStream] beforeStart() - Calling disableAutoFlowControl()");
                clientObserver.disableAutoFlowControl();
            }

            @Override
            public void startRequest() {
                System.out.println("[BiStream] startRequest() - stream ready, requesting initial message");
                if (clientObserver != null) {
                    clientObserver.request(1);
                }
            }

            @Override
            public void onNext(DataChunk chunk) {
                int count = received.incrementAndGet();
                System.out.println("[BiStream] Received response seq=" + chunk.getSequenceNumber() + ", total=" + count);

                // Request next message
                if (clientObserver != null) {
                    batchCount.incrementAndGet();
                    clientObserver.request(1);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("[BiStream] Error: " + throwable.getMessage());
                throwable.printStackTrace();
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("[BiStream] Stream completed, received " + received.get() + " responses");
                latch.countDown();
            }
        };

        // Start bidirectional stream
        StreamObserver<DataChunk> requestObserver = service.biStream(responseObserver);

        // Send messages
        byte[] data = new byte[100];
        for (int i = 0; i < sendCount; i++) {
            DataChunk chunk = new DataChunk(i, data, System.currentTimeMillis());
            System.out.println("[BiStream] Sending chunk seq=" + i);
            requestObserver.onNext(chunk);
            Thread.sleep(50);
        }
        requestObserver.onCompleted();

        latch.await(60, TimeUnit.SECONDS);
        System.out.println("[BiStream] Total requests made: " + batchCount.get());
    }
}

