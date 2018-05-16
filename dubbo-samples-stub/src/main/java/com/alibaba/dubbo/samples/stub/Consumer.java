package com.alibaba.dubbo.samples.stub;

import com.alibaba.dubbo.samples.stub.api.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            new String[] {"META-INF/spring/stub-consumer.xml"});
        context.start();
        DemoService demoService = (DemoService)context.getBean("demoService"); // get remote service proxy
        demoService.sayHello("aaa");


    }
}
