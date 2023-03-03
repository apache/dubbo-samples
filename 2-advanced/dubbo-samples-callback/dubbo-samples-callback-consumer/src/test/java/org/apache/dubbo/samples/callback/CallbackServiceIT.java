package org.apache.dubbo.samples.callback;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.callback.api.CallbackService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class CallbackServiceIT {
    @DubboReference
    private CallbackService service;

    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<String> results = new ArrayList<>();
        service.addListener("foo.bar", msg -> {
            results.add(msg);
            latch.countDown();
        });

        latch.await(5000, TimeUnit.MILLISECONDS);
        Assert.assertFalse(results.isEmpty());
        Assert.assertTrue(results.get(0).startsWith("Changed:"));
    }

}
