package com.alibaba.dubbo.samples.generic;

import com.alibaba.dubbo.samples.generic.api.IUserService;
import com.alibaba.dubbo.samples.generic.api.IUserService.Params;
import com.alibaba.dubbo.samples.generic.api.IUserService.User;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * GenericConsumer
 */
public class GenericConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/generic-consumer.xml"});
        context.start();
        IUserService userservice = (IUserService) context.getBean("userservice");
        User user = userservice.get(new Params("a=b"));
        System.out.println(user);
        System.in.read();
    }
}
