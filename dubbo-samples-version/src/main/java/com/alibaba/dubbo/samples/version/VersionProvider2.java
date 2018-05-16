package com.alibaba.dubbo.samples.version;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * VersionProvider2
 */
public class VersionProvider2 {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/version-provider2.xml"});
        context.start();
        System.in.read();
    }

}
