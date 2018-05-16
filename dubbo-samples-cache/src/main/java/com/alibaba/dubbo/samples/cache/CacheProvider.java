package com.alibaba.dubbo.samples.cache;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * CacheProvider
 */
public class CacheProvider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/cache-provider.xml"});
        context.start();
        System.in.read();
    }

}
