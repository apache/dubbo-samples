package com.alibaba.dubbo.samples.merge;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MergeProvider
 */
public class MergeProvider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/merge-provider.xml"});
        context.start();
        System.in.read();
    }

}
