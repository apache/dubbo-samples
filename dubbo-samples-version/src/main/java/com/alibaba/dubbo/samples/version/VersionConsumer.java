package com.alibaba.dubbo.samples.version;

import com.alibaba.dubbo.samples.version.api.VersionService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * VersionConsumer
 */
public class VersionConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/version-consumer.xml"});
        context.start();
        VersionService versionService = (VersionService) context.getBean("versionService");
        for (int i = 0; i < 10000; i++) {
            String hello = versionService.sayHello("world");
            System.out.println(hello);
            Thread.sleep(2000);
        }
        System.in.read();
    }

}
