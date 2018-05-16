package com.alibaba.dubbo.samples.echo;

import javax.sound.midi.Soundbank;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.samples.echo.api.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/echo-consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy

        EchoService echoService = (EchoService) demoService;
        String status = (String)echoService.$echo("OK");
        System.out.println("echo result: " + status);




    }
}
