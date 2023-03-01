package org.apache.dubbo.samples.stub.test;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.stub.DemoService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerIT {
    @DubboReference(check=false, stub="org.apache.dubbo.samples.stub.DemoServiceStub", interfaceName = "org.apache.dubbo.samples.stub.DemoService")
    private DemoService demoService;

    @Test
    public void test() {
        String result = demoService.sayHello("world");
        Assert.assertEquals("stub - greeting world", result);
    }
}
