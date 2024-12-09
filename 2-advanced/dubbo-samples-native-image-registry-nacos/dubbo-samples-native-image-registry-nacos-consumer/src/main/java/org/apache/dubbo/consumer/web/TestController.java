package org.apache.dubbo.consumer.web;

import org.apache.dubbo.api.DemoService;
import org.apache.dubbo.api.User;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨泽翰
 */
@RestController
public class TestController {
    @DubboReference
    private DemoService demoService;

    @GetMapping("/sayHello")
    public String sayHello() {
        return demoService.sayHello("user");
    }

    @GetMapping("/getUser")
    public User getUser() {
        return demoService.getUser("user");
    }
}
