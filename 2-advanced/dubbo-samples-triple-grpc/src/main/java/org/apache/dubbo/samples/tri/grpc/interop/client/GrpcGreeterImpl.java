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

package org.apache.dubbo.samples.tri.grpc.interop.client;

import org.apache.dubbo.samples.tri.grpc.GreeterReply;
import org.apache.dubbo.samples.tri.grpc.GreeterRequest;
import org.apache.dubbo.samples.tri.grpc.GreeterGrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcGreeterImpl extends GreeterGrpc.GreeterImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcGreeterImpl.class);

    @Override
    public void greet(GreeterRequest request, io.grpc.stub.StreamObserver<GreeterReply> responseObserver) {
        try {
            final GreeterReply response = GreeterReply.newBuilder()
                    .setMessage("hello," + request.getName())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public io.grpc.stub.StreamObserver<GreeterRequest> biStream(io.grpc.stub.StreamObserver<GreeterReply> responseObserver) {
        return new io.grpc.stub.StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                GreeterReply resp = GreeterReply.newBuilder().setMessage("reply from biStream " + data.getName()).build();
                responseObserver.onNext(resp);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };
    }

    @Override
    public void serverStream(GreeterRequest request, io.grpc.stub.StreamObserver<GreeterReply> responseObserver) {
        LOGGER.info("receive request: {}", request.getName());
        for (int i = 0; i < 10; i++) {
            GreeterReply reply = GreeterReply.newBuilder().setMessage("reply from serverStream. " + i).build();
            responseObserver.onNext(reply);
        }
        responseObserver.onCompleted();
    }
}