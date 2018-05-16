package com.alibaba.dubbo.samples.generic;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * GenericProvider
 */
public class GenericProvider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/generic-provider.xml"});
        context.start();
        System.in.read();
    }

}
