package org.apache.dubbo.provider.service;

import org.apache.dubbo.api.DemoService;
import org.apache.dubbo.api.User;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨泽翰
 */
@RestController
@DubboService
public class DemoServiceImpl implements DemoService {
    @GetMapping("/sayHello")
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @GetMapping("/getUser")
    @Override
    public User getUser(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(10);
        return user;
    }

}
