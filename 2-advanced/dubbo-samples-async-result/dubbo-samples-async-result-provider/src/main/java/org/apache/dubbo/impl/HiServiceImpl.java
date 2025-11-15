package org.apache.dubbo.impl;

import org.apache.dubbo.HiService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.CompletableFuture;

@DubboService
public class HiServiceImpl implements HiService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "async result: " + name;
        });
    }
}
