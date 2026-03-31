package org.apache.dubbo.sample.async.result.consumer;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.async.result.api.DemoService;

import java.util.concurrent.CompletableFuture;

public class ConsumerApplication {
    public static void main(String[] args) throws Exception {
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setInterface(DemoService.class);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("async-result-consumer"))
                 .reference(reference)
                 .start();

        DemoService demoService = reference.get();
        CompletableFuture<String> result = demoService.sayHello("Dubbo");
        System.out.println("Result: " + result.get());
    }
}

