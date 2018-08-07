package com.alibaba.dubbo.samples;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SwitchThreadProvider {
    /**
     * To get ipv6 address to work, add
     * System.setProperty("java.net.preferIPv6Addresses", "true");
     * before running your application.
     */
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-switch-thread-provider.xml"});
        context.start();
        System.in.read(); // press any key to exit
    }
}

