package org.apache.dubbo.samples.merge;

import org.apache.dubbo.samples.merge.api.IDubboService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        context.start();

        IDubboService dubboService = (IDubboService) context.getBean("dubboService");
        System.out.println(dubboService.hello());
    }
}
