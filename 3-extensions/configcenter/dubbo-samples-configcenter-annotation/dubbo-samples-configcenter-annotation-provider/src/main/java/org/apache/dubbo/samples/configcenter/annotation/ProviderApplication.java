package org.apache.dubbo.samples.configcenter.annotation;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ProviderApplication {

    public static void main(String[] args) {
        new EmbeddedZooKeeper(2181, false).start();
        ZKTools.generateDubboProperties();
        SpringApplication.run(ProviderApplication.class, args);

        System.out.println("dubbo service started");
    }

}
