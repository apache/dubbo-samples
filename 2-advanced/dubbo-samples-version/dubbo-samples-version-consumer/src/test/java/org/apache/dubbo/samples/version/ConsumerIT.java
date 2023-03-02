package org.apache.dubbo.samples.version;


import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.version.api.VersionService;
import org.junit.Assert;
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
    public void test() {
        boolean version1 = false;
        boolean version2 = false;

        for (int i = 0; i < 100; i++) {
            String result = versionService.sayHello("dubbo");
            System.out.println("result: " + result);
            if (result.equals("hello, dubbo")) {
                version1 = true;
            }
            if (result.equals("hello2, dubbo")) {
                version2 = true;
            }
            if (version1&&version2==true)break;
        }
        Assert.assertTrue(version1 && version2);
    }
}
