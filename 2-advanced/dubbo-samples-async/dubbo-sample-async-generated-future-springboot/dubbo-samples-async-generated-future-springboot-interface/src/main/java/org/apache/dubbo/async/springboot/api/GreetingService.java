package org.apache.dubbo.async.springboot.api;


import java.util.concurrent.CompletableFuture;

public interface GreetingService {

    String greeting(String name);

    default String replyGreeting(String name) {
        return "Fine, " + name;
    }

    default CompletableFuture<String> greeting(String name, byte signal) {
        return CompletableFuture.completedFuture(greeting(name));
    }

}