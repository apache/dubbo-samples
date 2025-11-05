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

package org.apache.dubbo.sample.tri.util;

import org.apache.dubbo.common.stream.StreamObserver;

import java.util.function.Function;

/**
 * EchoStreamObserver
 * @date 2021/9/6
 **/
public class EchoStreamObserver<T, R> implements StreamObserver<T> {

    private final Function<T, R> echoFunc;
    private final StreamObserver<R> responseObserver;

    public EchoStreamObserver(Function<T, R> echoFunc, StreamObserver<R> responseObserver) {
        this.echoFunc = echoFunc;
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(T data) {
        responseObserver.onNext(echoFunc.apply(data));
    }

    @Override
    public void onError(Throwable throwable) {
        responseObserver.onError(new IllegalStateException("Stream err"));
    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
    }
}
