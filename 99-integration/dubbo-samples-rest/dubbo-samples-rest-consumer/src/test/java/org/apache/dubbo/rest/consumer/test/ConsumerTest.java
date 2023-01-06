package org.apache.dubbo.rest.consumer.test;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.rest.api.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerTest {
    @DubboReference
    private DemoService demoService;
    @Test
    public void test01(){
        String hello = demoService.hello("1111");
        System.out.println(hello);
    }
}
