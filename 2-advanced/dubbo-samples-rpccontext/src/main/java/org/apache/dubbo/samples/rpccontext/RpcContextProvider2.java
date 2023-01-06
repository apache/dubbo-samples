package org.apache.dubbo.samples.rpccontext;

import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService2;
import org.apache.dubbo.samples.rpccontext.impl.RpcContextImpl1;
import org.apache.dubbo.samples.rpccontext.impl.RpcContextImpl2;
import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;


public class RpcContextProvider2 {
    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-rpccontext-provider2.xml");
        context.start();
        System.out.println("Rpc context provider2 started");
        new CountDownLatch(1).await();
    }
}