package com.alibaba.dubbo.samples.service.hello;

import com.alibaba.dubbo.samples.api.HelloService;

import java.util.Random;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String message) {
        try {
            Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
        } catch (InterruptedException e) {
            // no op
        }
        return "hello, " + message;
    }
}
