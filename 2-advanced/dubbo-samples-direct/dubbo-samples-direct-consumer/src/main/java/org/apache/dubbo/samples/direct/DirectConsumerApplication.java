package org.apache.dubbo.samples.direct;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DirectConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DirectConsumerApplication.class,args);
    }
}
