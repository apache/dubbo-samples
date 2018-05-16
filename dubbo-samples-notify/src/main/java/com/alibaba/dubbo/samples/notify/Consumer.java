package com.alibaba.dubbo.samples.notify;

import com.alibaba.dubbo.samples.notify.api.DemoService;
import com.alibaba.dubbo.samples.notify.impl.NotifyImpl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {

    public static void main(String[] args) throws Exception {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/notify-consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy
        NotifyImpl notify = (NotifyImpl)context.getBean("demoCallback");
        int id = 1;
        String result = demoService.sayHello(id);
        for(int i = 0; i < 10; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            }
            else {
                break;
            }
        }

        System.out.println(notify.ret.get(id));

    }
}

