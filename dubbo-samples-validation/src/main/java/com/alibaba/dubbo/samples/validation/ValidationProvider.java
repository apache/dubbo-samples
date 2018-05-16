package com.alibaba.dubbo.samples.validation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ValidationProvider
 */
public class ValidationProvider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/validation-provider.xml"});
        context.start();
        System.in.read();
    }

}
