package org.apache.dubbo.samples.async.boot.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.async.boot.HiService;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.CompletableFuture;

/**
 * @author:ax1an9
 * @date: 24/3/2023
 * @time: 10:21 PM
 */
public class Task implements CommandLineRunner {
    @DubboReference
    private HiService hiService;

    @Override
    public void run(String... args) throws Exception {
        hiService.sayHello("world");

        CompletableFuture<String> helloFuture = RpcContext.getContext().getCompletableFuture();
        helloFuture.whenComplete((retValue, exception) -> {
            if (exception == null) {
                System.out.println("return value: " + retValue);
            } else {
                exception.printStackTrace();
            }
        });

        CompletableFuture<String> f = RpcContext.getContext().asyncCall(() -> hiService.sayHello("async call request"));
        System.out.println("async call returned: " + f.get());

        RpcContext.getContext().asyncCall(() -> {
            hiService.sayHello("one way call request1");
        });
    }
}
