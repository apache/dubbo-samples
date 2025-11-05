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

package org.apache.dubbo.samples.tri.grpc.interop.server;

import org.apache.dubbo.samples.tri.grpc.GreeterGrpc;
import org.apache.dubbo.samples.tri.grpc.GreeterReply;
import org.apache.dubbo.samples.tri.grpc.GreeterRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GrpcClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcClient.class);

    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final GreeterGrpc.GreeterStub stub;

    public GrpcClient(int serverPort) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", serverPort)
                .usePlaintext()
                .build();

        this.blockingStub = GreeterGrpc.newBlockingStub(channel);
        this.stub = GreeterGrpc.newStub(channel);
    }

    public static void main(String[] args) throws IOException {
        GrpcClient client = new GrpcClient(50053);
        client.unary();
        client.stream();
        client.serverStream();
        System.in.read();
    }


    private void unary() {
        GreeterRequest request = GreeterRequest.newBuilder().setName("[grpc client]")
                .build();
        GreeterReply reply = blockingStub.greet(request);
        LOGGER.info("Grpc client received reply <- \"{}\"", reply.getMessage());
    }

    private void stream() {
        io.grpc.stub.StreamObserver<GreeterReply> responseObserver = new StdoutStreamObserver<GreeterReply>("[grpc client][bi-stream]");
        io.grpc.stub.StreamObserver<GreeterRequest> requestObserver = stub.biStream(responseObserver);
        int n = 10;
        for (int i = 0; i < n; i++) {
            GreeterRequest request = GreeterRequest.newBuilder()
                    .setName("[grpc client]-" + i)
                    .build();
            requestObserver.onNext(request);
        }
        responseObserver.onCompleted();
    }

    private void serverStream() {
        io.grpc.stub.StreamObserver<GreeterReply> responseObserver = new StdoutStreamObserver<GreeterReply>("[grpc client][server-stream]");
        GreeterRequest request = GreeterRequest.newBuilder()
                .setName("[grpc client]-" + "[server stream]")
                .build();
        stub.serverStream(request, responseObserver);
    }

    static class StdoutStreamObserver<T> implements io.grpc.stub.StreamObserver<T> {

        private static final Logger LOGGER = LoggerFactory.getLogger(StdoutStreamObserver.class);

        private final String name;

        public StdoutStreamObserver(String name) {
            this.name = name;
        }

        @Override
        public void onNext(T data) {
            LOGGER.info("{} stream <- reply:{}", name, data);
        }

        @Override
        public void onError(Throwable throwable) {
            LOGGER.error("{} stream onError", name, throwable);
            throwable.printStackTrace();
        }

        @Override
        public void onCompleted() {
            LOGGER.info("{} stream completed", name);
        }
    }

}