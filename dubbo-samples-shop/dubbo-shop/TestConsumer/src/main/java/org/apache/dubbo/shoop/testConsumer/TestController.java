package org.apache.dubbo.shoop.testConsumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class TestController {
    public static void main(String[] args) {
        SpringApplication.run(TestController.class,args);
    }
}
