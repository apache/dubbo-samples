package com.alibaba.dubbo.samples.notify;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/notify-provider.xml"});
        context.start();

        System.in.read(); // press any key to exit
    }

}
