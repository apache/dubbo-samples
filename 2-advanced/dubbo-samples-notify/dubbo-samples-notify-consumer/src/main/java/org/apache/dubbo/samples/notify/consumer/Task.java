package org.apache.dubbo.samples.notify.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.notify.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Task implements CommandLineRunner {

    @DubboReference
    private DemoService demoService;

    @Autowired
    private NotifyImpl notify;

    @Override
    public void run(String... args) throws Exception {
        int id = 1;
        String result = demoService.sayHello(id);
        for (int i = 0; i < 10; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }

        System.out.println("result: " + notify.ret.get(id));
    }
}
