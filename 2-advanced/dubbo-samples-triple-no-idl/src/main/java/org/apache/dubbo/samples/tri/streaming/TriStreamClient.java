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

package org.apache.dubbo.samples.tri.streaming;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.tri.streaming.util.TriSampleConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TriStreamClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriStreamClient.class);

    public static void main(String[] args) throws IOException {
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ReferenceConfig<Greeter> ref = new ReferenceConfig<>();
        ref.setInterface(Greeter.class);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setProxy(CommonConstants.NATIVE_STUB);
        ref.setTimeout(3000);

        ApplicationConfig applicationConfig = new ApplicationConfig("tri-stub-consumer");
        applicationConfig.setQosEnable(false);
        bootstrap.application(applicationConfig).reference(ref).registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS)).start();
        Greeter greeter = ref.get();

        //bi stream
        biStream(greeter);

        //server stream
        serverStream(greeter);
    }

    private static void biStream(Greeter greeter) {
        StreamObserver<GreeterRequest> requestStreamObserver = greeter.biStream(new SampleStreamObserver());
        for (int i = 0; i < 10; i++) {
            GreeterRequest request = GreeterRequest.newBuilder().setName("name-" + i).build();
            requestStreamObserver.onNext(request);
        }
        requestStreamObserver.onCompleted();
    }

    private static void serverStream(Greeter greeter) {
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