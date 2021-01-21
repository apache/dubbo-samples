package org.apache.dubbo.sc;

import org.apache.dubbo.demo.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/dubbo-consumer.xml")
public class ServiceDiscoveryIT {


    @Autowired
    private DemoService demoService;

    @Test
    public void test() {

        String hello = demoService.sayHello("world");
        System.out.println("result: " + hello);
        Assert.assertEquals("Hello world", hello);

    }
}
