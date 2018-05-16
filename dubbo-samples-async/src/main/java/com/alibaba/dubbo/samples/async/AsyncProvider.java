package com.alibaba.dubbo.samples.async;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AsyncProvider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/async-provider.xml"});
        context.start();
        System.in.read();
    }

}
