package com.alibaba.dubbo.samples.service.greeting;

import com.alibaba.dubbo.samples.api.GreetingService;
import com.alibaba.dubbo.samples.api.HelloService;

import java.util.Random;

public class GreetingServiceImpl implements GreetingService {

    private HelloService helloService;

    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String greeting(String message) {
        try {
            Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
        } catch (InterruptedException e) {
            // no op
        }
        return "greeting, " + helloService.hello(message);
    }
}
