package com.alibaba.dubbo.samples.group.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.samples.group.api.GroupService;

/**
 * @author zmx ON 2018/4/26
 */
public class GroupBServiceImpl implements GroupService{

    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext
            .getContext().getRemoteAddress() + "in groupB");
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress() + "group B";
    }
}
