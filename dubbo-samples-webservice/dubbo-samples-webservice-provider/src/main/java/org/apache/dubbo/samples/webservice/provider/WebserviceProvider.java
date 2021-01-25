package org.apache.dubbo.samples.webservice.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

public class WebserviceProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-samples-webservice-provider.xml");
        context.registerShutdownHook();
        context.start();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }

}
