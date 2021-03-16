package com.apache.dubbo.sample.basic;

import org.apache.dubbo.hello.HelloReply;
import org.apache.dubbo.hello.HelloRequest;

public class IGreeter1Impl implements IGreeter {
    @Override
    public HelloReply sayHello(HelloRequest request) {
        return HelloReply.newBuilder()
                .setMessage(request.getName())
                .build();
    }
}
