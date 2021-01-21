package org.apache.dubbo.sc;

import org.apache.dubbo.samples.microservices.sc.rest.User;
import org.apache.dubbo.samples.microservices.sc.rest.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/dubbo-consumer.xml")
public class DubboSCIT {

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        User user = userService.getUser(1L);
        System.out.println("result: " + user);
        Assert.assertEquals("username-1", user.getName());
    }
}
