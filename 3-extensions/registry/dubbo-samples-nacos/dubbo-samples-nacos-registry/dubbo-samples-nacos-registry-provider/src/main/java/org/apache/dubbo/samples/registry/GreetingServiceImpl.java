package org.apache.dubbo.samples.registry;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.nacos.registry.api.GreetingService;

@DubboService
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHello(String name) {
//        try {
//            // sleeping 5 seconds leads to TimeoutException on client side, and registry impl will be invoked
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("greeting service received: " + name);
        return "hello " + name;
    }
}
