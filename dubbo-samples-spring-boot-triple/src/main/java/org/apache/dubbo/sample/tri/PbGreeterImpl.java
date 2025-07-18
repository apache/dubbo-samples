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
package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.HashMap;
import java.util.Map;

@DubboService
public class PbGreeterImpl implements PbGreeter {

    public static final Map<String, Boolean> cancelResultMap = new HashMap<>();

    @Override
    public GreeterReply greetWithAttachment(GreeterRequest request) {
        final String key = "user-attachment";
        final String value = "hello," + RpcContext.getServerAttachment().getAttachment(key);
        RpcContext.getServerContext().setObjectAttachment(key, value);
        return GreeterReply.newBuilder().setMessage("hello," + request.getName()).build();
    }

    @Override
    public GreeterReply greet(GreeterRequest request) {
        System.out.println(request.getName());
        return GreeterReply.newBuilder()
                .setMessage(request.getName())
                .build();
    }

    @Override
    public GreeterReply greetException(GreeterRequest request) {
        RpcContext.getServerContext().setAttachment("str", "str")
                .setAttachment("integer", 1)
                .setAttachment("raw", new byte[]{1, 2, 3, 4});
        throw new RuntimeException("Biz Exception");
    }

    @Override
    public StreamObserver<GreeterRequest> greetStream(StreamObserver<GreeterReply> replyStream) {
        return new StreamObserver<GreeterRequest>() {
            int n = 0;

            @Override
            public void onNext(GreeterRequest data) {
                n++;
                System.out.println(data.getName() + " " + n);
                replyStream.onNext(GreeterReply.newBuilder()
                        .setMessage(data.getName() + " " + n)
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                replyStream.onError(new IllegalStateException("Stream err"));
            }

            @Override
            public void onCompleted() {
                System.out.println("[greetStream] onCompleted");
                replyStream.onCompleted();
            }
        };
    }

    @Override
    public void greetServerStream(GreeterRequest request, StreamObserver<GreeterReply> replyStream) {
        for (int i = 0; i < 10; i++) {
            replyStream.onNext(GreeterReply.newBuilder()
                    .setMessage(request.getName() + "--" + i)
                    .build());
        }
        replyStream.onCompleted();
    }
}