package org.apache.dubbo.async.springboot.consumer;

import org.apache.dubbo.async.springboot.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerIT {
    @DubboReference
    private GreetingService greetingService;

    @Test
    public void testAsync() throws Exception {
        CompletableFuture<String> future = greetingService.greeting("async call request", (byte) 0x01);
        Assert.assertEquals("Fine, async call request", future.get());
    }

    @Test
    public void testSync() throws Exception {
        Assert.assertEquals("Fine, sync call request", greetingService.greeting("sync call request"));
    }
}
