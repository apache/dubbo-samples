package org.apache.dubbo.nativeimage;


import java.util.concurrent.CompletableFuture;

public interface DemoService {

    HelloResponse sayHello(HelloRequest request);

    default CompletableFuture<HelloResponse> sayHelloAsync(HelloRequest request) {
        return CompletableFuture.completedFuture(sayHello(request));
    }
}
