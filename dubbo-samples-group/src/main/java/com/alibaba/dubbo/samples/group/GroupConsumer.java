package com.alibaba.dubbo.samples.group;

import com.alibaba.dubbo.samples.group.api.GroupService;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class GroupConsumer {

    public static void main(String[] args) {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/group-consumer.xml"});
        context.start();
        GroupService groupAService = (GroupService) context.getBean("groupAService"); // get remote service proxy
        GroupService groupBService = (GroupService)context.getBean("groupBService");

        while (true) {
            try {
                Thread.sleep(1000);
                String resultGroupA = groupAService.sayHello("world"); // call remote method
                System.out.println(resultGroupA); // get result

                String resultGroupB = groupBService.sayHello("world");
                System.out.println(resultGroupB); // get result

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }


        }

    }
}
