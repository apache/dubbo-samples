package org.apache.dubbo.shop.shippingService;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ShippingServiceApplication {
    public static void main(String[] args) {
            SpringApplication.run(ShippingServiceApplication.class, args);
    }
}
