package org.apache.dubbo.samples.async.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.async.api.GreetingService;

@DubboService
public class GreetingServiceImpl implements GreetingService {
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
