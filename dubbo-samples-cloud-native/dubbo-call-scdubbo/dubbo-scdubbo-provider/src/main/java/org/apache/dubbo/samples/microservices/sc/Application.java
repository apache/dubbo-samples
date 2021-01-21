package org.apache.dubbo.samples.microservices.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients()
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}