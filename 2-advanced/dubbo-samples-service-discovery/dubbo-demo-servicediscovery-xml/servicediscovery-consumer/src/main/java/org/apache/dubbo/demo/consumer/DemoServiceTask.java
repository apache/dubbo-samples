package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.demo.DemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoServiceTask implements CommandLineRunner {

    @DubboReference
    DemoService demoService;
    @Override
    public void run(String... args) {
        System.out.println("demo test start ");
        for (int i=0;i<10;i++){
            String response=demoService.sayHello(String.valueOf(i));
            System.out.println("response ===>  "+response);
        }
        System.out.println("finally");
    }

}
