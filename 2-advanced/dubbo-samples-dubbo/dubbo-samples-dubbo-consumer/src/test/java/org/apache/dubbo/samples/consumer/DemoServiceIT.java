package org.apache.dubbo.samples.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.protocol.dubbo.demo.DemoService;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {DubboAutoConfiguration.class})
@RunWith(SpringRunner.class)
public class DemoServiceIT {
    @DubboReference
    private DemoService demoService;

    @Test
    public void service() throws Exception {
        Assert.assertTrue(demoService.sayHello("dubbo").startsWith("Hello dubbo"));
    }


}
