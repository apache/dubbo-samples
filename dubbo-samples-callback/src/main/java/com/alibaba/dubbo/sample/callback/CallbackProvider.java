package com.alibaba.dubbo.sample.callback;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MergeProvider
 */
public class CallbackProvider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/callback-provider.xml"});
        context.start();
        System.in.read();
    }

}
