package org.apache.dubbo.samples.registry.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.nacos.registry.api.GreetingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Task implements CommandLineRunner {
    @DubboReference
    private GreetingService greetingService;

    @Override
    public void run(String... args) throws Exception {
        String result = greetingService.sayHello("nacos");
        System.out.println("Receive result ======> " + result);

//        new Thread(()-> {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                    System.out.println(new Date() + " Receive result ======> " + greetingService.sayHello("world"));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }).start();
    }
}
