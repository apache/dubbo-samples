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

package com.apache.dubbo.sample.basic;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.hello.HelloReply;
import org.apache.dubbo.hello.HelloRequest;

public class StreamConsumer {
    public static void main(String[] args) throws InterruptedException, IOException {
        ReferenceConfig<IStreamGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(IStreamGreeter.class);
        ref.setCheck(false);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);
        ref.setTimeout(100000);
        ref.setApplication(new ApplicationConfig("stream-consumer"));
        ref.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        final IStreamGreeter iStreamGreeter = ref.get();

        System.out.println("dubbo ref started");
        try {

            StreamObserver<HelloRequest> streamObserver = iStreamGreeter.sayHello(new StreamObserver<HelloReply>() {
                @Override
                public void onNext(HelloReply reply) {
                    System.out.println("onNext");
                    System.out.println(reply.getMessage());
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("onError:" + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("onCompleted");
                }
            });

            streamObserver.onNext(HelloRequest.newBuilder()
                .setName("tony")
                .build());

            streamObserver.onNext(HelloRequest.newBuilder()
                .setName("nick")
                .build());

            streamObserver.onCompleted();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.in.read();
    }
}
