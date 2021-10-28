package org.apache.dubbo.sample.tri.service.impl;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.sample.tri.helper.EchoStreamObserver;
import org.apache.dubbo.sample.tri.service.WrapGreeter;

public class WrapGreeterImpl implements WrapGreeter {
    @Override
    public String overload() {
        return "overload";
    }

    @Override
    public String overload(String param) {
        return param;
    }

    @Override
    public String sayHelloLong(int len) {
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
    public String sayHello(String request) {
        return "hello," + request;
    }

    @Override
    public void sayHelloResponseVoid(String request) {
        System.out.println("call void response");
    }

    @Override
    public String sayHelloRequestVoid() {
        return "hello!void";
    }

    @Override
    public String sayHelloException(String request) {
        throw new IllegalStateException("Biz exception");
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
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println(data);
                response.onNext("hello," + data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
                response.onCompleted();
            }
        };
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
