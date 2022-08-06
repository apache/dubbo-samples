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

package org.apache.dubbo.sample.tri.migration;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.sample.tri.util.EchoStreamObserver;

public class IGreeterImpl implements IWrapperGreeter {

    @Override
    public String sayHello(String request) {
        return "hello," + request;
    }

    @Override
    public void sayHelloResponseVoid(String request) {
        System.out.println("call void response");
    }

    @Override
    public String sayHelloRequestVoid() {
        System.out.println("call void request");
        return "hello!";
    }

    @Override
    public String sayHelloException(String request) {
        throw new RuntimeException("Biz exception");
    }

    @Override
    public String sayHelloWithAttachment(String request) {
        System.out.println(RpcContext.getServerAttachment().getObjectAttachments());
        RpcContext.getServerContext().setAttachment("str", "str")
                .setAttachment("integer", 1)
                .setAttachment("raw", new byte[]{1, 2, 3, 4});
        return "hello," + request;
    }

    @Override
    public StreamObserver<String> sayHelloStream(StreamObserver<String> response) {
        return new EchoStreamObserver<>(str -> "hello," + str, response);
    }

    @Override
    public StreamObserver<String> sayHelloStreamError(StreamObserver<String> response) {
        response.onError(new Throwable("ServerStream error"));
        return new EchoStreamObserver<>(str -> "hello," + str, response);
    }

    @Override
    public void sayHelloServerStream(String request, StreamObserver<String> response) {
        for (int i = 0; i < 10; i++) {
            response.onNext("hello," + request);
        }
        response.onCompleted();
    }
}
