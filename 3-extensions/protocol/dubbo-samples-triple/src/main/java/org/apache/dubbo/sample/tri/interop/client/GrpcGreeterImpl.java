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

package org.apache.dubbo.sample.tri.interop.client;

import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.Context;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import org.apache.dubbo.sample.tri.DubboGreeterTriple;
import org.apache.dubbo.sample.tri.GreeterGrpc;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.apache.dubbo.sample.tri.stub.GreeterImpl;
import org.apache.dubbo.sample.tri.util.GrpcStreamObserverAdapter;
import org.apache.dubbo.sample.tri.util.StreamObserverAdapter;

import java.util.HashMap;
import java.util.Map;

public class GrpcGreeterImpl extends GreeterGrpc.GreeterImplBase {

    public static final Map<String, Boolean> cancelResultMap = new HashMap<>();

    private final DubboGreeterTriple.GreeterImplBase delegate;

    @Override
    public void greetReturnBigAttachment(GreeterRequest request,
                                         StreamObserver<GreeterReply> responseObserver) {
        delegate.greetReturnBigAttachment(request, new StreamObserverAdapter<>(responseObserver));
    }

    @Override
    public StreamObserver<GreeterRequest> cancelBiStream(
            StreamObserver<GreeterReply> responseObserver) {
        Context.current().addListener(
                context -> cancelResultMap.put("cancelBiStream", true)
                , MoreExecutors.directExecutor());
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                responseObserver.onNext(GreeterReply.newBuilder()
                        .setMessage(data.getName())
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<GreeterRequest> cancelBiStream2(
            StreamObserver<GreeterReply> responseObserver) {
        Context.current().addListener(
                context -> {
                    cancelResultMap.put("cancelBiStream2", true);
                }
                , MoreExecutors.directExecutor());
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                responseObserver.onNext(GreeterReply.newBuilder()
                        .setMessage(data.getName())
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<GreeterRequest> compressorBiStream(
            StreamObserver<GreeterReply> responseObserver) {
        ServerCallStreamObserver<GreeterReply> replyServerStreamObserver = (ServerCallStreamObserver<GreeterReply>) responseObserver;
        replyServerStreamObserver.setCompression("gzip");
        return getGreeterRequestStreamObserver(replyServerStreamObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> clientCompressorBiStream(
            StreamObserver<GreeterReply> responseObserver) {
        ServerCallStreamObserver<GreeterReply> replyServerStreamObserver = (ServerCallStreamObserver<GreeterReply>) responseObserver;
        return getGreeterRequestStreamObserver(replyServerStreamObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> serverCompressorBiStream(
            StreamObserver<GreeterReply> responseObserver) {
        ServerCallStreamObserver<GreeterReply> replyServerStreamObserver = (ServerCallStreamObserver<GreeterReply>) responseObserver;
        replyServerStreamObserver.setCompression("gzip");
        return getGreeterRequestStreamObserver(replyServerStreamObserver);
    }

    @Override
    public void cancelServerStream(GreeterRequest request,
                                   StreamObserver<GreeterReply> responseObserver) {
        Context.current().addListener(
                context -> {
                    cancelResultMap.put("cancelServerStream", true);
                }
                , MoreExecutors.directExecutor());
        for (int i = 0; i < 10; i++) {
            responseObserver.onNext(GreeterReply.newBuilder()
                    .setMessage(request.getName() + "--" + i)
                    .build());
        }
    }

    @Override
    public void queryCancelResult(GreeterRequest request,
                                  StreamObserver<GreeterReply> responseObserver) {
        boolean canceled = cancelResultMap.getOrDefault(request.getName(), false);
        responseObserver.onNext(GreeterReply.newBuilder()
                .setMessage(String.valueOf(canceled))
                .build());
        responseObserver.onCompleted();
    }

    public GrpcGreeterImpl() {
        this.delegate = new GreeterImpl("grpc");
    }

    @Override
    public void greet(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        try {
            final GreeterReply response = delegate.greet(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public void greetException(GreeterRequest request,
                               StreamObserver<GreeterReply> responseObserver) {
        try {
            final GreeterReply response = delegate.greetException(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public StreamObserver<GreeterRequest> greetStream(
            StreamObserver<GreeterReply> responseObserver) {
        return new GrpcStreamObserverAdapter<>(
                delegate.greetStream(new StreamObserverAdapter<>(responseObserver)));
    }

    @Override
    public void greetWithAttachment(GreeterRequest request,
                                    StreamObserver<GreeterReply> responseObserver) {
        try {
            final GreeterReply response = delegate.greetWithAttachment(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public void greetServerStream(GreeterRequest request,
                                  StreamObserver<GreeterReply> responseObserver) {
        delegate.greetServerStream(request, new StreamObserverAdapter<>(responseObserver));
    }

    private StreamObserver<GreeterRequest> getGreeterRequestStreamObserver(
            StreamObserver<GreeterReply> streamObserver) {
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                streamObserver.onNext(GreeterReply.newBuilder()
                        .setMessage(data.getName())
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                streamObserver.onError(new IllegalStateException("Stream err"));
            }

            @Override
            public void onCompleted() {
                streamObserver.onCompleted();
            }
        };
    }
}