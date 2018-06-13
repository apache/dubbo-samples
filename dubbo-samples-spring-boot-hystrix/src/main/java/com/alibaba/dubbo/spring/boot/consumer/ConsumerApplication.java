package com.alibaba.dubbo.spring.boot.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.HelloService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@Service
@EnableHystrix
public class ConsumerApplication {

    @Reference(version = "1.0.0")
    private HelloService demoService;

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(ConsumerApplication.class, args);
        ConsumerApplication application = context.getBean(ConsumerApplication.class);

        // server 端的hystrix会抛出异常，本地的调用结果会走 fallbackMethod
        // 证明server 和 client端都经过 hystrix处理
        String result = application.doSayHello("world");
        System.err.println("result :" + result);
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }

    public String reliable(String name) {
        return "hystrix fallback value";
    }
}
