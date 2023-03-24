package org.apache.dubbo.async.springboot.provider;

import org.apache.dubbo.async.springboot.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class GreetingsServiceImpl implements GreetingService {
    @Override
    public String greeting(String name) {
        System.out.println("provider received: " + name);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("provider returned.");
        return replyGreeting(name);
    }
}
