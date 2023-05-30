package org.apache.dubbo.nativeimage.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages={"org.apache.dubbo.nativeimage.provider"})
@EnableDubbo(scanBasePackages = {"org.apache.dubbo.nativeimage.provider"})
public class NativeDemoProviderApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(NativeDemoProviderApplication.class, args);
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
