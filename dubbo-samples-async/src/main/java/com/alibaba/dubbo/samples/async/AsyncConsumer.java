package com.alibaba.dubbo.samples.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.samples.async.api.AsyncService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * CallbackConsumer
 */
public class AsyncConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/async-consumer.xml"});
        context.start();

        final AsyncService asyncService = (AsyncService) context.getBean("asyncService");

        Future<String> f = RpcContext.getContext().asyncCall(new Callable<String>() {
            public String call() throws Exception {
                return asyncService.sayHello("async call request");
            }
        });

        System.out.println("async call ret :" + f.get());


        RpcContext.getContext().asyncCall(new Runnable() {
            public void run() {
                asyncService.sayHello("oneway call request1");
                asyncService.sayHello("oneway call request2");
            }
        });

        System.in.read();
    }

}
