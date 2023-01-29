package org.apache.dubbo.samples.metrics.prometheus;

import org.apache.dubbo.samples.metrics.prometheus.api.DemoService;
import org.apache.dubbo.samples.metrics.prometheus.model.Result;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class StartProviderAndConsumer {
    public static void main(String[] args) {
        new EmbeddedZooKeeper(2181, false).start();

        startProvider();
        startConsumer();
    }

    private static void startConsumer() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-demo-consumer.xml");
        context.start();

        DemoService demoService = context.getBean("demoService", DemoService.class);

        while (true) {
            try {
                Thread.sleep(3000);
                Result hello = demoService.sayHello("world");
                System.out.println(hello.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void startProvider() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-demo-provider.xml");
        context.start();

        System.out.println("dubbo service started");
    }
}
