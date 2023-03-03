package org.apache.dubbo.samples.local;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.local.api.LocalService;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {DubboAutoConfiguration.class})
@RunWith(SpringRunner.class)
public class LocalServiceIT {
    @DubboReference
    private LocalService localService;

    @Test
    public void test() throws Exception {
        String result = localService.sayHello("world");
        Assert.assertEquals(result, "Hello world, response from provider: 10.193.180.32:20880");
        result = localService.sayHelloAsync("world");
        Assert.assertEquals(result, "Hello world, response from provider: 10.193.180.32:20880");
    }
}
