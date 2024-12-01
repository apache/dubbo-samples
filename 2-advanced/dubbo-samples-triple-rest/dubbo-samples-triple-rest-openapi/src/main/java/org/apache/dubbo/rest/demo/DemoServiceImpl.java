package org.apache.dubbo.rest.demo;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public String hello(User user, int count) {
        return "Hello " + user.getTitle() + ". " + user.getName() + ", " + count;
    }

    @Override
    public String helloUser(User user) {
        return "Hello " + user.getTitle() + ". " + user.getName();
    }
}
