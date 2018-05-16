package com.alibaba.dubbo.sample.callback;

import com.alibaba.dubbo.sample.callback.api.CallbackListener;
import com.alibaba.dubbo.sample.callback.api.CallbackService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * CallbackConsumer
 */
public class CallbackConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/callback-consumer.xml"});
        context.start();
        CallbackService callbackService = (CallbackService) context.getBean("callbackService");
        callbackService.addListener("foo.bar", new CallbackListener() {
            public void changed(String msg) {
                System.out.println("callback1:" + msg);
            }
        });
        System.in.read();
    }

}
