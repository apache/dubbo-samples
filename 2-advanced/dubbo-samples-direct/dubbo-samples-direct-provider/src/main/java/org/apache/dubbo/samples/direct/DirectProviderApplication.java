package org.apache.dubbo.samples.direct;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DirectProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DirectProviderApplication.class, args);
    }
}
