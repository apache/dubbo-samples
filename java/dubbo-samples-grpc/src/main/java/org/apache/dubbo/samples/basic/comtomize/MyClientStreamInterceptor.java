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
import org.apache.dubbo.rpc.protocol.grpc.interceptors.ClientInterceptor;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;

/**
 * This interceptor works at the client side and intercepts all
 * outgoing request messages and incoming response messages
 */
@Activate(group = CONSUMER)
public class MyClientStreamInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> methodDescriptor,
            CallOptions callOptions,
            Channel channel) {

        final ClientCall<ReqT, RespT> wrappedCall = channel.newCall(methodDescriptor, callOptions);
        return new StreamRequestClientCall<>(wrappedCall);

    }

    /**
     * intercept any streaming request message or any streaming status change.
     *
     * @param <ReqT>
     * @param <RespT>
     */
    private static class StreamRequestClientCall<ReqT, RespT>
            extends ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT> {

        public StreamRequestClientCall(final ClientCall<ReqT, RespT> wrappedCall) {
            super(wrappedCall);
        }

        @Override
        public void start(final ClientCall.Listener<RespT> responseListener, Metadata headers) {
            super.start(new StreamResponseListener<>(responseListener), headers);
        }

        @Override
        public void sendMessage(ReqT reqMessage) {
            // add your logic here
            System.out.println("Sending request msg to server: " + reqMessage);
            super.sendMessage(reqMessage);
        }
    }

    /**
     * intercept any streaming response message or any streaming status change.
     * @param <RespT>
     */
    private static class StreamResponseListener<RespT>
            extends ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT> {

        public StreamResponseListener(final ClientCall.Listener<RespT> responseListener) {
            super(responseListener);
        }

        @Override
        public void onMessage(RespT respMessage) {
            // add your logic here
            System.out.println("Received msg from server: " + respMessage);
            super.onMessage(respMessage);
        }
    }
}
