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
package org.apache.dubbo.tri.websocket.demo;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DubboService
public class DemoServiceImpl implements DemoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }

    @Override
    public String sayHelloError(String name) {
        throw new RuntimeException("test error: " + name);
    }

    @Override
    public void greetServerStream(String request, StreamObserver<String> response) {
        for (int i = 0; i < 10; i++) {
            response.onNext("Hello, " + request + i);
        }
        response.onCompleted();
    }

    @Override
    public void greetServerStreamError(String request, StreamObserver<String> response) {
        response.onError(new RuntimeException("test error: " + request));
    }

    @Override
    public void greetServerStreamDirectError(String request, StreamObserver<String> response) {
        throw new RuntimeException("test direct error: " + request);
    }

    @Override
    public StreamObserver<String> greetBiStream(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                LOGGER.info(data);
                response.onNext("Hello, " + data);
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("GreetBiStream on error", throwable);
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
                response.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<String> greetBiStreamError(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                response.onError(new RuntimeException("test error: " + data));
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("GreetBiStream on error", throwable);
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
                response.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<String> greetBiStreamDirectError(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                throw new RuntimeException("test direct error: " + data);
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.error("GreetBiStream on error", throwable);
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
                response.onCompleted();
            }
        };
    }
}
