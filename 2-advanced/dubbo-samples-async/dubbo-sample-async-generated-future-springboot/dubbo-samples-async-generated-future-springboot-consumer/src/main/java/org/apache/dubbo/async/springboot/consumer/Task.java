package org.apache.dubbo.async.springboot.consumer;

import org.apache.dubbo.async.springboot.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class Task implements CommandLineRunner {
    @DubboReference
    GreetingService greetingService;

    private static final byte SIGNAL = 1;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    CompletableFuture<String> future = greetingService.greeting("async call request", SIGNAL);
                    System.out.println("async call returned: " + future.get());

                    System.out.println(greetingService.greeting("normal sync call request"));
                    Thread.sleep(1000);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }).start();
    }
}
