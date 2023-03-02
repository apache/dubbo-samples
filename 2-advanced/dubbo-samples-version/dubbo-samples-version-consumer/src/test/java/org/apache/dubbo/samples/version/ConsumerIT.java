package org.apache.dubbo.samples.version;


import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.version.api.VersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerIT {

    @DubboReference
    private VersionService versionService;

    @Test
    public void test(String... args) throws Exception {
        for (int i = 0; i < 10000; i++) {
            String hello = versionService.sayHello("world");
            System.out.println(hello);
            Thread.sleep(2000);
        }
    }
}
