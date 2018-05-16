package com.alibaba.dubbo.samples.attachment;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AttachmentProvider {

    public static void main(String[] args) throws Exception{
        new EmbeddedZooKeeper(2181, false).start();
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/attachment-provider.xml"});
        context.start();

        System.in.read(); // press any key to exit
    }
}
