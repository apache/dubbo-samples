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

package org.apache.dubbo.sample.tri.pojo;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.sample.tri.Greeter;
import org.apache.dubbo.sample.tri.api.ChildPojo;
import org.apache.dubbo.sample.tri.api.ParentPojo;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.stub.GreeterImpl;
import org.apache.dubbo.sample.tri.util.EchoStreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class PojoGreeterImpl implements PojoGreeter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PojoGreeterImpl.class);
    private final Greeter delegate;

    public PojoGreeterImpl() {
        this.delegate = new GreeterImpl("tri-wrap");
    }

    @Override
    public CompletableFuture<String> unaryFuture(String request) {
        return CompletableFuture.completedFuture(request);
    }

    @Override
    public String methodParamIsNull(String request) {
        System.out.println("methodParamIsNull request:" + request);
        assert request == null;
        return "ok";
    }

    @Override
    public ParentPojo greetChildPojo(Byte test) {
        ChildPojo childPojo = new ChildPojo();
        childPojo.setChild("test");
        childPojo.setParent("test");
        childPojo.setByte1(test);
        return childPojo;
    }

    @Override
    public String overload() {
        return "overload";
    }

    @Override
    public String methodNotFound() {
        return "methodNotFound";
    }

    @Override
    public String overload(String param) {
        return param;
    }

    @Override
    public String greetLong(int len) {
        StringBuilder respBuilder = new StringBuilder();
        if (len > 0) {
            respBuilder.append("a");
        }
        for (; respBuilder.length() < len; respBuilder.append(respBuilder)) {
            respBuilder.append(respBuilder);
        }
        return respBuilder.substring(0, len);
    }

    @Override
    public String greet(String request) {
        return "hello," + request;
    }

    @Override
    public void greetResponseVoid(String request) {
        LOGGER.info("call void response");
    }

    @Override
    public String sayGreeterRequestVoid() {
        return "hello!void";
    }

    @Override
    public String greetException(String request) {
        throw new IllegalStateException("Biz exception");
    }

    @Override
    public String greetWithAttachment(String request) {
        LOGGER.info("{} Received request attachments:{}", "", RpcContext.getServerAttachment().getObjectAttachments());
        RpcContext.getServerContext().setAttachment("str", "str")
                .setAttachment("integer", 1)
                .setAttachment("raw", new byte[]{1, 2, 3, 4});
        return "hello," + request;
    }

    @Override
    public StreamObserver<String> greetStream(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                LOGGER.info(data);
                response.onNext("hello," + data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
                response.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<String> greetStreamError(StreamObserver<String> response) {
        response.onError(new Throwable("ServerStream error"));
        return new EchoStreamObserver<>(str -> "hello," + str, response);
    }

    @Override
    public void greetServerStream(String request, StreamObserver<String> response) {
        for (int i = 0; i < 10; i++) {
            response.onNext("hello," + request);
        }
        response.onCompleted();
    }
}
