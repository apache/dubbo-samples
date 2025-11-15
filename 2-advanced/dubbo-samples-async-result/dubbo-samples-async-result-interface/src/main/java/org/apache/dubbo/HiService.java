package org.apache.dubbo;

import java.util.concurrent.CompletableFuture;

public interface HiService {
    public String sayHello(String name);

    public CompletableFuture<String> sayHelloAsync(String name);

}
