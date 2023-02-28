package org.apache.dubbo.samples.direct.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.direct.api.DirectService;


import java.text.SimpleDateFormat;
import java.util.Date;

@DubboService
public class DirectServiceImpl implements DirectService {
    @Override
    public String sayHello(String name) {
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " +
                    name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
            return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
