package com.alibaba.dubbo.samples.merge;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MergeProvider2
 */
public class MergeProvider2 {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/merge-provider2.xml"});
        context.start();
        System.in.read();
    }

}
