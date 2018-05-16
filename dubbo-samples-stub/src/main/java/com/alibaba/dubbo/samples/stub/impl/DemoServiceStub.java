package com.alibaba.dubbo.samples.stub.impl;

import com.alibaba.dubbo.samples.stub.api.DemoService;

/**
 * @author zmx ON 2018/4/27
 */
public class DemoServiceStub implements DemoService{

    private final DemoService demoService;

    public DemoServiceStub(DemoService demoService) {
        this.demoService = demoService;
    }

    public String sayHello(String name) {
        //client check code goes here
        System.out.println("stub sayHello");

        try {
            return demoService.sayHello(name);
        } catch (Exception e) {
           //handle Exception
            return null;
        }
    }
}
