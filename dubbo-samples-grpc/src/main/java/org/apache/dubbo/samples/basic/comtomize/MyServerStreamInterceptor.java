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
package org.apache.dubbo.samples.basic.comtomize;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.protocol.grpc.interceptors.ServerInterceptor;

import io.grpc.ForwardingServerCall;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * This interceptor works at the server side and intercepts all
 * incoming request messages and outgoing response messages
 */
@Activate(group = PROVIDER)
public class MyServerStreamInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall,
            Metadata requestHeaders,
            ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> listener = next.startCall(new StreamResponseServerCall<>(serverCall), requestHeaders);
        return new StreamRequestListener<>(listener);

    }

    /**
     * intercept any streaming response message or any streaming status change.
     *
     * @param <ReqT>
     * @param <RespT>
     */
    private static class StreamResponseServerCall<ReqT, RespT>
            extends ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT> {

        public StreamResponseServerCall(ServerCall<ReqT, RespT> serverCall) {
            super(serverCall);
        }

        @Override
        public void sendMessage(RespT message) {
            // add you logic here
            System.out.println("Sending response message back to client: " + message);
            super.sendMessage(message);
        }

        @Override
        public void request(int numMessages) {
            // add your logic here
            System.out.println("Requesting " + numMessages + " more messages from client.");
            super.request(numMessages);
        }

    }

    /**
     * intercept any streaming request message or any streaming status change.
     * @param <ReqT>
     */
    private static class StreamRequestListener<ReqT>
            extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {

        public StreamRequestListener(ServerCall.Listener<ReqT> requestListener) {
            super(requestListener);
        }

        @Override
        public void onMessage(ReqT message) {
            // add your logic here
            System.out.println("Received request message from client: " + message);
            super.onMessage(message);
        }

        @Override
        public void onComplete() {
            // add your logic here
            System.out.println("Completed, all requests finished.");
            super.onComplete();
        }
    }
}
