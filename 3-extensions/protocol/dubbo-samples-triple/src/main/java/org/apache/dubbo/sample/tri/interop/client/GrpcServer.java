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

import org.apache.dubbo.common.context.Lifecycle;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class GrpcServer implements Lifecycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServer.class);

    private final int port;
    private Server server;

    public GrpcServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer(TriSampleConstants.GRPC_SERVER_PORT);
        server.initialize();
        server.start();
        System.in.read();
    }

    @Override
    public void initialize() throws IllegalStateException {
        this.server = ServerBuilder.forPort(port)
                .addService(new GrpcGreeterImpl())
                .intercept(new EchoAttachmentInterceptor())
                .build();
    }

    @Override
    public void start() throws IllegalStateException {
        try {
            server.start();
            LOGGER.info("Grpc server started at port {}", port);
        } catch (IOException e) {
            throw new IllegalStateException("Start grpc server failed ", e);
        }
    }

    @Override
    public void destroy() throws IllegalStateException {
        server.shutdown();
    }

    private static class EchoAttachmentInterceptor implements ServerInterceptor {
        @Override
        public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
                ServerCall<ReqT, RespT> serverCall,
                Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
            ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT> forwardingCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(serverCall) {
                @Override
                public void close(Status status, Metadata trailers) {
                    final String key = "user-attachment";
                    final Metadata.Key<String> metaKey = Metadata.Key.of(key,
                            Metadata.ASCII_STRING_MARSHALLER);
                    if (metadata.containsKey(metaKey)) {
                        trailers.put(metaKey, "hello," + Objects.requireNonNull(
                                metadata.get(metaKey)));
                    }
                    super.close(status, trailers);
                }
            };
            return serverCallHandler.startCall(forwardingCall, metadata);
        }
    }
}
