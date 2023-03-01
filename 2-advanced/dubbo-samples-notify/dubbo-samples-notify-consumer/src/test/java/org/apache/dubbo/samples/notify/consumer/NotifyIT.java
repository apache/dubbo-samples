package org.apache.dubbo.samples.notify.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.notify.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NotifyIT {
    @DubboReference
    private DemoService demoService;

    @Autowired
    private NotifyImpl notify;

    @Test
    public void testDemoService() throws Exception {
        String result = demoService.sayHello(1);
        Assert.assertEquals("demo1", result);
    }

    @Test
    public void testOnReturn() throws Exception {
        int id = 2;
        demoService.sayHello(id);
        for (int i = 0; i < 10; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }

        Assert.assertEquals("demo2", notify.ret.get(id));
    }

    @Test
    public void testOnThrow() throws Exception {
        int id = 11;
        try {
            demoService.sayHello(id);
        } catch (Throwable t) {
            // ignore
        }

        for (int i = 0; i < 10; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }

        Assert.assertTrue(notify.ret.get(id) instanceof RuntimeException);
        Exception e = (Exception) notify.ret.get(id);
        Assert.assertEquals("exception from sayHello: too large id", e.getMessage());
    }
}
