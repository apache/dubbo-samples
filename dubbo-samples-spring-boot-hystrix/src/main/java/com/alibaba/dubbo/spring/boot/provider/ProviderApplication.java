package com.alibaba.dubbo.spring.boot.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication
@EnableHystrix
@EnableDubbo(scanBasePackages = { "com.example" })
public class ProviderApplication {

    public static void main(String[] args) {

        // start embedded zookeeper server
        new EmbeddedZooKeeper(2181, false).start();

        SpringApplication.run(ProviderApplication.class, args);
    }

}
