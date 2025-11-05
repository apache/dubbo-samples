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

package org.apache.dubbo.sample.tri.interop.server;

import org.apache.dubbo.sample.tri.GreeterGrpc;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.apache.dubbo.sample.tri.util.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
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
        GrpcClient client = new GrpcClient(TriSampleConstants.SERVER_PORT);
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
        StreamObserver<GreeterReply> responseObserver = new StdoutStreamObserver<GreeterReply>("[grpc client][bi-stream]").asGrpcObserver();
        StreamObserver<GreeterRequest> requestObserver = stub.greetStream(responseObserver);
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
        StreamObserver<GreeterReply> responseObserver = new StdoutStreamObserver<GreeterReply>("[grpc client][server-stream]").asGrpcObserver();
        GreeterRequest request = GreeterRequest.newBuilder()
                .setName("[grpc client]-" + "[server stream]")
                .build();
        stub.greetServerStream(request, responseObserver);
    }

}