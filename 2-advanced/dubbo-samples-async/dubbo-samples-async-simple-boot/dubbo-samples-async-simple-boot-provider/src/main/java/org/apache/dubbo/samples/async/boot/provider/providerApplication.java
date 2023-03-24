package org.apache.dubbo.samples.async.boot.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author:ax1an9
 * @date: 24/3/2023
 * @time: 10:13 PM
 */
@SpringBootApplication
@EnableDubbo
public class providerApplication {
    public static void main(String[] args) {
        SpringApplication.run(providerApplication.class,args);
    }
}
