package org.apache.dubbo.samples.multi.registry;


import org.apache.dubbo.samples.multi.registry.api.DemoService;
import org.apache.dubbo.samples.multi.registry.api.HelloService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/consumer.xml")
public class MultiRegistryServiceIT {
    @Autowired
    @Qualifier("demoServiceFormBeijing")
    DemoService demoServiceFormBeijing;

    @Autowired
    @Qualifier("helloServiceFormShanghai")
    HelloService helloServiceFormShanghai;

    @Autowired
    @Qualifier("helloServiceFormBeijing")
    HelloService helloServiceFormBeijing;

    @Test
    public void getTest() {
        Assert.assertEquals("get: service form beijing registry",
                demoServiceFormBeijing.get("service form beijing registry"));
    }

    @Test
    public void sayHelloTest() {
        Assert.assertEquals("sayHello: service form shanghai registry",
                helloServiceFormShanghai.sayHello("service form shanghai registry"));
        Assert.assertEquals("sayHello: service form beijing registry",
                helloServiceFormBeijing.sayHello("service form beijing registry"));
    }
}
