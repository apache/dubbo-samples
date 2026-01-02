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
import org.apache.dubbo.remoting.http12.FlowControlStreamObserver;
import org.apache.dubbo.samples.backpressure.api.BackpressureService;
import org.apache.dubbo.samples.backpressure.api.DataChunk;
import org.apache.dubbo.samples.backpressure.api.StreamRequest;
import org.apache.dubbo.samples.backpressure.api.StreamResponse;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation of BackpressureService demonstrating streaming with backpressure.
 */
public class BackpressureServiceImpl implements BackpressureService {

    @Override
    public String echo(String message) {
        System.out.println("[Server] Echo received: " + message);
        return "Echo: " + message;
    }

    @Override
    public void serverStream(StreamRequest request, StreamObserver<DataChunk> responseObserver) {
        int count = request.getCount();
        int chunkSize = request.getChunkSize();
        System.out.println("[Server] Starting server stream, count=" + count + ", chunkSize=" + chunkSize);
        
        // Generate and send chunks
        byte[] data = new byte[chunkSize];
        for (int i = 0; i < count; i++) {
            DataChunk chunk = new DataChunk(i, data, System.currentTimeMillis());
            responseObserver.onNext(chunk);
            
            if ((i + 1) % 20 == 0) {
                System.out.println("[Server] Sent " + (i + 1) + " chunks");
            }
        }
        
        responseObserver.onCompleted();
        System.out.println("[Server] Server stream completed, sent " + count + " chunks");
    }

    /**
     * Client streaming with SERVER-SIDE backpressure demonstration.
     * 
     * The responseObserver passed in implements FlowControlStreamObserver,
     * which can be used to control inbound message flow rate.
     */
    @Override
    public StreamObserver<DataChunk> clientStream(StreamObserver<StreamResponse> responseObserver) {
        System.out.println("[Server-ClientStream] Client stream started");
        final long startTime = System.currentTimeMillis();
        final AtomicInteger chunkCount = new AtomicInteger(0);
        final AtomicLong totalBytes = new AtomicLong(0);
        final int batchSize = 5; // Server requests 5 messages at a time
        final AtomicInteger batchCount = new AtomicInteger(0);

        // Cast to FlowControlStreamObserver to enable server-side backpressure
        final FlowControlStreamObserver<StreamResponse> flowControlObserver;
        if (responseObserver instanceof FlowControlStreamObserver) {
            flowControlObserver = (FlowControlStreamObserver<StreamResponse>) responseObserver;
            
            // Enable server-side backpressure
            System.out.println("[Server-ClientStream] Calling disableAutoFlowControl()");
            flowControlObserver.disableAutoFlowControl();
            
            // Request initial batch
            System.out.println("[Server-ClientStream] Calling request(" + batchSize + ") for initial batch");
            flowControlObserver.request(batchSize);
            
            System.out.println("[Server-ClientStream] Server-side backpressure enabled!");
        } else {
            flowControlObserver = null;
            System.out.println("[Server-ClientStream] WARNING: responseObserver is not FlowControlStreamObserver");
            System.out.println("[Server-ClientStream] Observer type: " + responseObserver.getClass().getName());
        }

        return new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk chunk) {
                int count = chunkCount.incrementAndGet();
                if (chunk.getData() != null) {
                    totalBytes.addAndGet(chunk.getData().length);
                }
                
                System.out.println("[Server-ClientStream] Received chunk seq=" + chunk.getSequenceNumber() + ", total=" + count);
                
                // Simulate slow processing on server side
                try {
                    Thread.sleep(20); // 20ms processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Request next batch after processing current batch
                if (count % batchSize == 0 && flowControlObserver != null) {
                    int batch = batchCount.incrementAndGet();
                    System.out.println("[Server-ClientStream] >>> Requesting next batch (batch #" + batch + ", request " + batchSize + " more)");
                    flowControlObserver.request(batchSize);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("[Server-ClientStream] Error: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                long duration = System.currentTimeMillis() - startTime;
                StreamResponse response = new StreamResponse(
                        chunkCount.get(),
                        totalBytes.get(),
                        duration
                );
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                System.out.println("[Server-ClientStream] Completed: received " + chunkCount.get() + 
                        " chunks in " + duration + "ms, batches requested: " + batchCount.get());
            }
        };
    }

    /**
     * Bidirectional streaming with SERVER-SIDE backpressure demonstration.
     * 
     * Server controls how fast it receives messages from client,
     * while also sending responses back.
     */
    @Override
    public StreamObserver<DataChunk> biStream(StreamObserver<DataChunk> responseObserver) {
        System.out.println("[Server-BiStream] BiStream started");
        final int batchSize = 3; // Server requests 3 messages at a time
        final AtomicInteger receivedCount = new AtomicInteger(0);
        final AtomicInteger batchCount = new AtomicInteger(0);

        // Cast to FlowControlStreamObserver to enable server-side backpressure
        final FlowControlStreamObserver<DataChunk> flowControlObserver;
        if (responseObserver instanceof FlowControlStreamObserver) {
            flowControlObserver = (FlowControlStreamObserver<DataChunk>) responseObserver;
            
            // Enable server-side backpressure
            System.out.println("[Server-BiStream] Calling disableAutoFlowControl()");
            flowControlObserver.disableAutoFlowControl();
            
            // Request initial batch
            System.out.println("[Server-BiStream] Calling request(" + batchSize + ") for initial batch");
            flowControlObserver.request(batchSize);
            
            System.out.println("[Server-BiStream] Server-side backpressure enabled!");
        } else {
            flowControlObserver = null;
            System.out.println("[Server-BiStream] WARNING: responseObserver is not FlowControlStreamObserver");
            System.out.println("[Server-BiStream] Observer type: " + responseObserver.getClass().getName());
        }

        return new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk requestChunk) {
                int count = receivedCount.incrementAndGet();
                System.out.println("[Server-BiStream] Received chunk seq=" + requestChunk.getSequenceNumber() + ", total=" + count);
                
                // Simulate slow processing on server side
                try {
                    Thread.sleep(30); // 30ms processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Echo back with modified sequence number
                DataChunk responseChunk = new DataChunk(
                        requestChunk.getSequenceNumber() * 10,
                        requestChunk.getData(),
                        System.currentTimeMillis()
                );
                responseObserver.onNext(responseChunk);
                
                // Request next batch after processing current batch
                if (count % batchSize == 0 && flowControlObserver != null) {
                    int batch = batchCount.incrementAndGet();
                    System.out.println("[Server-BiStream] >>> Requesting next batch (batch #" + batch + ", request " + batchSize + " more)");
                    flowControlObserver.request(batchSize);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("[Server-BiStream] Error: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
                System.out.println("[Server-BiStream] Completed: received " + receivedCount.get() + 
                        " chunks, batches requested: " + batchCount.get());
            }
        };
    }
}

