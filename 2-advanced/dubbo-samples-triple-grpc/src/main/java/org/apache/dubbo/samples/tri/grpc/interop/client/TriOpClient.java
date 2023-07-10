/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.dubbo.samples.tri.grpc.interop.client;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.tri.grpc.Greeter;
import org.apache.dubbo.samples.tri.grpc.GreeterReply;
import org.apache.dubbo.samples.tri.grpc.GreeterRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TriOpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriOpClient.class);

    private static Greeter greeter;

    public static void main(String[] args) throws IOException {
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ReferenceConfig<Greeter> ref = new ReferenceConfig<>();
        ref.setInterface(Greeter.class);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setProxy(CommonConstants.NATIVE_STUB);
        ref.setTimeout(3000);
        ref.setUrl("tri://localhost:50051");

        bootstrap.application(new ApplicationConfig("tri-stub-consumer"))
                .reference(ref)
                .start();

        greeter = ref.get();

        unary();
        biStream();
        serverStream();

        System.in.read();
    }

    public static void unary() {
        LOGGER.info("Start unary");
        final GreeterReply reply = greeter.greet(GreeterRequest.newBuilder()
                .setName("name")
                .build());
        LOGGER.info("Unary reply <-{}", reply);
    }

    private static void biStream() {
        StreamObserver<GreeterRequest> requestStreamObserver = greeter.biStream(new SampleStreamObserver());
        for (int i = 0; i < 10; i++) {
            GreeterRequest request = GreeterRequest.newBuilder().setName("name-" + i).build();
            requestStreamObserver.onNext(request);
        }
        requestStreamObserver.onCompleted();
    }

    private static void serverStream() {
        GreeterRequest request = GreeterRequest.newBuilder().setName("server stream request.").build();
        greeter.serverStream(request, new SampleStreamObserver());
    }

    private static class SampleStreamObserver implements StreamObserver<GreeterReply> {

        @Override
        public void onNext(GreeterReply data) {
            LOGGER.info("stream <- reply:{}", data);
        }

        @Override
        public void onError(Throwable throwable) {
            LOGGER.error("stream onError", throwable);
            throwable.printStackTrace();
        }

        @Override
        public void onCompleted() {
            LOGGER.info("stream completed");
        }
    }
}