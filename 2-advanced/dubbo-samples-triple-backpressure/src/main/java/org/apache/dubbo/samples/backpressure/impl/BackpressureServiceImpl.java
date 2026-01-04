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

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.remoting.http12.h2.Http2ServerChannelObserver;
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

        Object obj = responseObserver;
        if (obj instanceof Http2ServerChannelObserver) {
            Http2ServerChannelObserver serverObserver = (Http2ServerChannelObserver) obj;
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
     * The CLIENT-SIDE backpressure is demonstrated in the consumer.
     */
    @Override
    public StreamObserver<DataChunk> clientStream(StreamObserver<StreamResponse> responseObserver) {
        LOGGER.info("[Server] Client stream started - receiving data from client");
        final long startTime = System.currentTimeMillis();
        final AtomicInteger chunkCount = new AtomicInteger(0);
        final AtomicLong totalBytes = new AtomicLong(0);

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
}
