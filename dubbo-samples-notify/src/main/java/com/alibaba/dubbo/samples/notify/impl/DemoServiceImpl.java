package com.alibaba.dubbo.samples.notify.impl;

import com.alibaba.dubbo.samples.notify.api.DemoService;


public class DemoServiceImpl implements DemoService{

    public String sayHello(int id) {
        return "aaa";
    }
}
