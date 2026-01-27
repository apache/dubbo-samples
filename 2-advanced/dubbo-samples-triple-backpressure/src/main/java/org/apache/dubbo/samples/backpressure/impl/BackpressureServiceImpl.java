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
package org.apache.dubbo.samples.backpressure.impl;

import org.apache.dubbo.common.stream.CallStreamObserver;
import org.apache.dubbo.common.stream.ServerCallStreamObserver;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.samples.backpressure.api.BackpressureService;
import org.apache.dubbo.samples.backpressure.api.DataChunk;
import org.apache.dubbo.samples.backpressure.api.StreamRequest;
import org.apache.dubbo.samples.backpressure.api.StreamResponse;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of BackpressureService demonstrating streaming with backpressure.
 */
public class BackpressureServiceImpl implements BackpressureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackpressureServiceImpl.class);

    @Override
    public String echo(String message) {
        LOGGER.info("[Server] Echo received: {}", message);
        return "Echo: " + message;
    }

    /**
     * Server streaming with SERVER-SIDE backpressure.
     */
    @Override
    public void serverStream(StreamRequest request, StreamObserver<DataChunk> responseObserver) {
        int totalCount = request.getCount();
        int chunkSize = request.getChunkSize();
        LOGGER.info("[Server-Backpressure] Starting server stream, count={}, chunkSize={}", totalCount, chunkSize);

        // Use ServerCallStreamObserver interface for backpressure
        if (responseObserver instanceof ServerCallStreamObserver) {
            ServerCallStreamObserver<DataChunk> serverObserver =
                    (ServerCallStreamObserver<DataChunk>) responseObserver;
            final AtomicInteger sentCount = new AtomicInteger(0);
            final AtomicBoolean completed = new AtomicBoolean(false);
            final byte[] data = new byte[chunkSize];

            // Set ready callback - called when the stream becomes writable
            serverObserver.setOnReadyHandler(() -> {
                LOGGER.info("[Server-Backpressure] onReadyHandler triggered, sent so far: {}", sentCount.get());

                // Send data while stream is ready and we have more to send
                while (serverObserver.isReady() && sentCount.get() < totalCount && !completed.get()) {
                    int seq = sentCount.getAndIncrement();
                    DataChunk chunk = new DataChunk(seq, data, System.currentTimeMillis());
                    serverObserver.onNext(chunk);

                    if ((seq + 1) % 10 == 0) {
                        LOGGER.info("[Server-Backpressure] Sent {} chunks (isReady={})", seq + 1, serverObserver.isReady());
                    }
                }

                // Complete if all data sent
                if (sentCount.get() >= totalCount && !completed.getAndSet(true)) {
                    serverObserver.onCompleted();
                    LOGGER.info("[Server-Backpressure] Completed via onReadyHandler, total: {}", sentCount.get());
                }
            });

            // Initial send (if writable)
            LOGGER.info("[Server-Backpressure] Initial send, isReady={}", serverObserver.isReady());
            while (serverObserver.isReady() && sentCount.get() < totalCount) {
                int seq = sentCount.getAndIncrement();
                DataChunk chunk = new DataChunk(seq, data, System.currentTimeMillis());
                serverObserver.onNext(chunk);

                if ((seq + 1) % 10 == 0) {
                    LOGGER.info("[Server-Backpressure] Initial sent {} chunks", seq + 1);
                }
            }

            // Complete if all sent in initial phase
            if (sentCount.get() >= totalCount && !completed.getAndSet(true)) {
                serverObserver.onCompleted();
                LOGGER.info("[Server-Backpressure] All sent in initial phase, total: {}", sentCount.get());
            } else {
                LOGGER.info("[Server-Backpressure] Paused at {} chunks, waiting for onReadyHandler", sentCount.get());
            }
        }

    }

    /**
     * Client streaming with SERVER-SIDE receive backpressure.
     *
     * <p>This demonstrates how server controls the rate of receiving data from client
     * using {@code disableAutoRequest()} and {@code request(int)} APIs
     *
     * <p>Dubbo API reference (aligned with gRPC):
     * <ul>
     *   <li>{@code ServerCallStreamObserver.disableAutoRequest()} - Disable automatic message requesting</li>
     *   <li>{@code ServerCallStreamObserver.request(int)} - Manually request messages from client</li>
     * </ul>
     */
    @Override
    public StreamObserver<DataChunk> clientStream(StreamObserver<StreamResponse> responseObserver) {
        LOGGER.info("[Server-ReceiveBackpressure] Client stream started - receiving data from client with backpressure");
        final long startTime = System.currentTimeMillis();
        final AtomicInteger chunkCount = new AtomicInteger(0);
        final AtomicLong totalBytes = new AtomicLong(0);

        // Cast to ServerCallStreamObserver for receive backpressure control ()
        if (responseObserver instanceof ServerCallStreamObserver) {
            ServerCallStreamObserver<StreamResponse> serverObserver =
                    (ServerCallStreamObserver<StreamResponse>) responseObserver;

            // Disable automatic message requesting - server will manually control receive rate
            serverObserver.disableAutoRequest();
            LOGGER.info("[Server-ReceiveBackpressure] Disabled auto request, server will control receive rate");

            // Request initial batch of messages
            final int initialRequest = 5;
            serverObserver.request(initialRequest);
            LOGGER.info("[Server-ReceiveBackpressure] Requested initial {} messages", initialRequest);

            return new StreamObserver<DataChunk>() {
                @Override
                public void onNext(DataChunk chunk) {
                    int count = chunkCount.incrementAndGet();
                    if (chunk.getData() != null) {
                        totalBytes.addAndGet(chunk.getData().length);
                    }

                    if (count % 10 == 0) {
                        LOGGER.info("[Server-ReceiveBackpressure] Received {} chunks from client", count);
                    }

                    // After processing each message, request the next one
                    // This is the key to server-side receive backpressure control
                    serverObserver.request(1);
                }

                @Override
                public void onError(Throwable throwable) {
                    LOGGER.error("[Server-ReceiveBackpressure] Client stream error: {}", throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    long duration = System.currentTimeMillis() - startTime;
                    StreamResponse response = new StreamResponse(chunkCount.get(), totalBytes.get(), duration);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    LOGGER.info("[Server-ReceiveBackpressure] Client stream completed: {} chunks, {} bytes in {}ms",
                            chunkCount.get(), totalBytes.get(), duration);
                }
            };
        }

        // Fallback: basic implementation without backpressure control
        LOGGER.warn("[Server] Fallback to basic implementation without receive backpressure");
        return new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk chunk) {
                int count = chunkCount.incrementAndGet();
                if (chunk.getData() != null) {
                    totalBytes.addAndGet(chunk.getData().length);
                }
                if (count % 10 == 0) {
                    LOGGER.info("[Server] Received {} chunks from client", count);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[Server] Client stream error: {}", throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                long duration = System.currentTimeMillis() - startTime;
                StreamResponse response = new StreamResponse(chunkCount.get(), totalBytes.get(), duration);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                LOGGER.info("[Server] Client stream completed: {} chunks, {} bytes in {}ms",
                        chunkCount.get(), totalBytes.get(), duration);
            }
        };
    }

    /**
     * Bidirectional streaming with SERVER-SIDE send and receive backpressure.
     *
     * <p>This demonstrates complete server-side backpressure control:
     * <ul>
     *   <li>Send backpressure: Using {@code setOnReadyHandler()} and {@code isReady()} to control sending rate</li>
     *   <li>Receive backpressure: Using {@code disableAutoRequest()} and {@code request(int)} to control receiving rate</li>
     * </ul>
     *
     * <p>Dubbo API reference (aligned with gRPC):
     * <ul>
     *   <li>{@code ServerCallStreamObserver.setOnReadyHandler(Runnable)} - Callback when stream becomes writable</li>
     *   <li>{@code ServerCallStreamObserver.isReady()} - Check if stream is ready to accept more data</li>
     *   <li>{@code ServerCallStreamObserver.disableAutoRequest()} - Disable automatic message requesting</li>
     *   <li>{@code ServerCallStreamObserver.request(int)} - Manually request messages from client</li>
     * </ul>
     */
    @Override
    public StreamObserver<DataChunk> biStream(StreamObserver<DataChunk> responseObserver) {
        LOGGER.info("[Server-BiStream-Backpressure] BiStream started - bidirectional streaming with full backpressure control");
        final AtomicInteger receivedCount = new AtomicInteger(0);
        final AtomicInteger sentCount = new AtomicInteger(0);

        // Cast to ServerCallStreamObserver for full backpressure control
        if (responseObserver instanceof ServerCallStreamObserver) {
            ServerCallStreamObserver<DataChunk> serverObserver =
                    (ServerCallStreamObserver<DataChunk>) responseObserver;

            // Queue to buffer chunks that couldn't be sent immediately due to backpressure
            java.util.concurrent.ConcurrentLinkedQueue<DataChunk> pendingChunks =
                    new java.util.concurrent.ConcurrentLinkedQueue<>();
            final AtomicBoolean streamCompleted = new AtomicBoolean(false);

            // === Send Backpressure Control ===
            // Set onReadyHandler to send pending chunks when stream becomes writable
            serverObserver.setOnReadyHandler(() -> {
                LOGGER.info("[Server-BiStream-Backpressure] onReadyHandler triggered, isReady={}, pending={}",
                        serverObserver.isReady(), pendingChunks.size());

                // Send pending chunks while stream is ready
                while (serverObserver.isReady() && !pendingChunks.isEmpty()) {
                    DataChunk chunk = pendingChunks.poll();
                    if (chunk != null) {
                        serverObserver.onNext(chunk);
                        int sent = sentCount.incrementAndGet();
                        if (sent % 10 == 0) {
                            LOGGER.info("[Server-BiStream-Backpressure] Sent {} response chunks via onReadyHandler", sent);
                        }
                    }
                }

                // Complete the stream if all chunks sent and stream is completed
                if (streamCompleted.get() && pendingChunks.isEmpty()) {
                    serverObserver.onCompleted();
                    LOGGER.info("[Server-BiStream-Backpressure] Completed via onReadyHandler: received={}, sent={}",
                            receivedCount.get(), sentCount.get());
                }
            });

            // === Receive Backpressure Control ===
            // Disable automatic message requesting - server will manually control receive rate
            serverObserver.disableAutoRequest();
            LOGGER.info("[Server-BiStream-Backpressure] Disabled auto request for receive backpressure");

            // Request initial batch of messages
            final int initialRequest = 5;
            serverObserver.request(initialRequest);
            LOGGER.info("[Server-BiStream-Backpressure] Requested initial {} messages, configured send backpressure with setOnReadyHandler", initialRequest);

            return new StreamObserver<DataChunk>() {
                @Override
                public void onNext(DataChunk chunk) {
                    int count = receivedCount.incrementAndGet();
                    if (count % 10 == 0) {
                        LOGGER.info("[Server-BiStream-Backpressure] Received {} chunks, isReady={}",
                                count, serverObserver.isReady());
                    }

                    // Prepare response chunk
                    DataChunk response = new DataChunk(
                            chunk.getSequenceNumber(),
                            chunk.getData(),
                            System.currentTimeMillis()
                    );

                    // Try to send immediately if stream is ready, otherwise buffer
                    if (serverObserver.isReady()) {
                        serverObserver.onNext(response);
                        int sent = sentCount.incrementAndGet();
                        if (sent % 10 == 0) {
                            LOGGER.info("[Server-BiStream-Backpressure] Sent {} response chunks directly", sent);
                        }
                    } else {
                        // Buffer the chunk - onReadyHandler will send it when stream becomes writable
                        pendingChunks.offer(response);
                        LOGGER.debug("[Server-BiStream-Backpressure] Buffered chunk, pending={}", pendingChunks.size());
                    }

                    // Request next message (receive backpressure control)
                    serverObserver.request(1);
                }

                @Override
                public void onError(Throwable throwable) {
                    LOGGER.error("[Server-BiStream-Backpressure] Error: {}", throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    streamCompleted.set(true);
                    // If all pending chunks sent, complete now; otherwise onReadyHandler will complete
                    if (pendingChunks.isEmpty()) {
                        serverObserver.onCompleted();
                        LOGGER.info("[Server-BiStream-Backpressure] Completed immediately: received={}, sent={}",
                                receivedCount.get(), sentCount.get());
                    } else {
                        LOGGER.info("[Server-BiStream-Backpressure] Waiting for pending chunks to be sent: {}",
                                pendingChunks.size());
                    }
                }
            };
        }

        // Fallback: basic implementation without backpressure control
        LOGGER.warn("[Server-BiStream] Fallback to basic implementation without backpressure");
        CallStreamObserver<DataChunk> callObserver = (CallStreamObserver<DataChunk>) responseObserver;

        return new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk chunk) {
                int count = receivedCount.incrementAndGet();
                if (count % 10 == 0) {
                    LOGGER.info("[Server-BiStream] Received {} chunks", count);
                }
                DataChunk response = new DataChunk(
                        chunk.getSequenceNumber(),
                        chunk.getData(),
                        System.currentTimeMillis()
                );
                callObserver.onNext(response);
                sentCount.incrementAndGet();
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("[Server-BiStream] Error: {}", throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                callObserver.onCompleted();
                LOGGER.info("[Server-BiStream] Completed: received={}, sent={}",
                        receivedCount.get(), sentCount.get());
            }
        };
    }
}
