package com.apache.dubbo.sample.basic;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class ApiWrapperConsumer {
    public static void main(String[] args) {
        ReferenceConfig<IGreeter2> ref = new ReferenceConfig<>();
        ref.setInterface(IGreeter2.class);
        ref.setCheck(false);
        ref.setProtocol("tri");
        ref.setLazy(true);
        ref.setApplication(new ApplicationConfig("demo-consumer"));
        ref.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        final IGreeter2 iGreeter = ref.get();
        System.out.println("dubbo ref started");
        long st = System.currentTimeMillis();
        String reply = iGreeter.sayHello0("haha");
        // 4MB response
        System.out.println("Reply len:" + reply.length() + " cost:" + (System.currentTimeMillis() - st));
    }
}
