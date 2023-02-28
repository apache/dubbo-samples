package org.apache.dubbo.samples.direct;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.direct.api.DirectService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class Task implements CommandLineRunner {
    @DubboReference
    private DirectService directService;

    @Override
    public void run(String... args) throws Exception {
        String hello = directService.sayHello("world");
        System.out.println(hello);

    }
}
