package org.apache.dubbo.samples.registry;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.nacos.registry.api.GreetingService;

@DubboService
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHello(String name) {
        System.out.println("greeting service received: " + name);
        return "hello " + name;
    }
}
