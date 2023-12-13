package org.apache.dubbo.springboot.demo.provider.dubbo.service;

import org.apache.dubbo.springboot.demo.DemoService;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
