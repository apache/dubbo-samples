package org.apache.dubbo.samples.rpccontext;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;

import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class RpcContextConsumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-rpccontext-consumer.xml");
        context.start();
        RpcContextService1 rpcContextService1 = context.getBean("consumerService", RpcContextService1.class);
        rpcContextService1.sayHello();
    }
}
