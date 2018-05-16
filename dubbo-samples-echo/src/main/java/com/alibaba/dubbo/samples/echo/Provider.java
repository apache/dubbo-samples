package com.alibaba.dubbo.samples.echo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zmx ON 2018/4/26
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/echo-provider.xml"});
        context.start();

        System.in.read(); // press any key to exit
    }
}
