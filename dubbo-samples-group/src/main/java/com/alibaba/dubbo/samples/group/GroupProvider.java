package com.alibaba.dubbo.samples.group;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class GroupProvider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/group-provider.xml"});
        context.start();

        System.in.read(); // press any key to exit
    }
}
