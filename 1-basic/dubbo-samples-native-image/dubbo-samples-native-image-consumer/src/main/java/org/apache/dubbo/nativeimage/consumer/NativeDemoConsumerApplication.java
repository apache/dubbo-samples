package org.apache.dubbo.nativeimage.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.nativeimage.DemoService;
import org.apache.dubbo.nativeimage.HelloRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"org.apache.dubbo.nativeimage.consumer"})
@EnableDubbo(scanBasePackages = {"org.apache.dubbo.nativeimage.consumer"})
public class NativeDemoConsumerApplication {

    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(NativeDemoConsumerApplication.class, args);
        NativeDemoConsumerApplication application = context.getBean(NativeDemoConsumerApplication.class);
        String result = application.doSayHello("world");
        System.out.println("result: " + result);
    }

    public String doSayHello(String name) {
        return demoService.sayHello(new HelloRequest(name)).getResponse();
    }

}
