package com.alibaba.dubbo.samples.jetty.impl;

import com.alibaba.dubbo.samples.jetty.HelloWorld;
import com.alibaba.dubbo.samples.jetty.JettyContainer;
import com.alibaba.dubbo.samples.jetty.api.JettyService;

public class JettyServiceImpl implements JettyService {

    private static JettyContainer container = new JettyContainer();

    @Override
    public void sayHello() {
        container.setServerHandler(new HelloWorld());

        container.start();

        container.stop();
    }

}
