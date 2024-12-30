package org.apache.dubbo.rest.openapi.advance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenAPIApplication.class, args);
        System.out.println("dubbo service started");
    }
}
