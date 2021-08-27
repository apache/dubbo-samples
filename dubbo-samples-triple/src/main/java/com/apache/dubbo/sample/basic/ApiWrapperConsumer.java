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

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

public class ApiWrapperConsumer {
    private static IGreeter2 iGreeter;

    public static void main(String[] args) {
        ReferenceConfig<IGreeter2> ref = new ReferenceConfig<>();
        ref.setInterface(IGreeter2.class);
        ref.setCheck(false);
        ref.setTimeout(3000);
        ref.setProtocol("tri");
        ref.setLazy(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-consumer"))
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(ref)
                .start();

        iGreeter = ref.get();
        System.out.println("dubbo ref started");
        sayHelloUnary();
        sayHelloUnaryResponseVoid();
        sayHelloUnaryRequestVoid();
        sayHelloLong();
        sayHelloException();
        sayHelloStream();
        sayHelloServerStream();
    }

    public static void sayHelloUnaryRequestVoid() {
        System.out.println(iGreeter.sayHelloRequestVoid());
    }

    public static void sayHelloUnaryResponseVoid() {
        iGreeter.sayHelloResponseVoid("void");
    }

    public static void sayHelloUnary() {
        System.out.println(iGreeter.sayHello("unary"));
    }

    public static void sayHelloException() {
        try {
            System.out.println(iGreeter.sayHelloException("exception"));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void sayHelloServerStream() {
        iGreeter.sayHelloServerStream("server stream", new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("Stream reply:" + data);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Stream error");
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream complete");
            }
        });

    }

    public static void sayHelloStream() {
        final StreamObserver<String> request = iGreeter.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("Stream reply:" + data);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Stream error");
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream complete");
            }
        });
        for (int i = 0; i < 10; i++) {
            request.onNext("stream request");
        }
        request.onCompleted();
    }

    public static void sayHelloLong() {
        final String response = iGreeter.sayHelloLong("unary long");
        System.out.println("Say hello long reply_size=" + response.length());
    }
}
