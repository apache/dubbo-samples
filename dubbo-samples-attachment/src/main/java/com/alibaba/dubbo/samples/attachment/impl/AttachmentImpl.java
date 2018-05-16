package com.alibaba.dubbo.samples.attachment.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.samples.attachment.api.AttachmentService;


public class AttachmentImpl implements AttachmentService{

    public String sayHello(String name) {

        String index = RpcContext.getContext().getAttachment("index");  //the attachment will be remove after this
        System.out.println("receive attachment index: " + index);

        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext
            .getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
