package org.apache.dubbo.custom.uri.demo;

import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.CompletableFuture;

@DubboService
public class DemoServiceImpl implements Greeter {

    @Override
    public HelloReply sayHelloWithPost(HelloRequest request) {
        String message = "POST Hello, " + request.getName() + "!";
        return HelloReply.newBuilder().setMessage(message).build();
    }

    @Override
    public CompletableFuture<HelloReply> sayHelloWithPostAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> sayHelloWithPost(request));
    }

    @Override
    public HelloReply updateGreeting(HelloRequest request) {
        if (request == null || request.getName() == null) {
            return HelloReply.newBuilder().setMessage("Request or name is missing").build();
        }

        String message = "Updated greeting to: " + request.getName();
        return HelloReply.newBuilder().setMessage(message).build();
    }

    @Override
    public CompletableFuture<HelloReply> updateGreetingAsync(HelloRequest request) {
        return CompletableFuture.supplyAsync(() -> updateGreeting(request));
    }
}
