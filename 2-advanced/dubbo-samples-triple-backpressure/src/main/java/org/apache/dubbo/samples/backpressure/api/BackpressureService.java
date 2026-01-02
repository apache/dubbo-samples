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
package org.apache.dubbo.samples.backpressure.api;

import org.apache.dubbo.common.stream.StreamObserver;

/**
 * Service interface for demonstrating backpressure with Triple protocol.
 */
public interface BackpressureService {

    /**
     * Unary call - for verifying basic functionality is not affected.
     */
    String echo(String message);

    /**
     * Server streaming - server sends multiple responses.
     * Used to test client-side backpressure.
     *
     * @param request stream request containing count and chunk size
     * @param responseObserver observer to receive chunks
     */
    void serverStream(StreamRequest request, StreamObserver<DataChunk> responseObserver);

    /**
     * Client streaming - client sends multiple requests.
     * Used to test server-side backpressure.
     *
     * @param responseObserver observer to receive final response
     * @return observer to send chunks
     */
    StreamObserver<DataChunk> clientStream(StreamObserver<StreamResponse> responseObserver);

    /**
     * Bidirectional streaming - both sides send multiple messages.
     * Used to test bidirectional backpressure.
     *
     * @param responseObserver observer to receive chunks
     * @return observer to send chunks
     */
    StreamObserver<DataChunk> biStream(StreamObserver<DataChunk> responseObserver);
}


