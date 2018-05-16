package com.alibaba.dubbo.samples.attachment;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.samples.attachment.api.AttachmentService;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AttachmentConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/attachment-consumer.xml"});
        context.start();
        AttachmentService attachmentService = (AttachmentService) context.getBean("demoService"); // get remote service proxy

        RpcContext.getContext().setAttachment("index", "1");
        String hello = attachmentService.sayHello("world");
        System.out.println(hello); // get result


        hello = attachmentService.sayHello("world"); //attachment only affective once
        System.out.println(hello); // get result

    }
}
