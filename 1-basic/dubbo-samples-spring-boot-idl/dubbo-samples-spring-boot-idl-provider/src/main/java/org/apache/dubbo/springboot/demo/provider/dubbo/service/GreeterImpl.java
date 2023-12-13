package org.apache.dubbo.springboot.demo.provider.dubbo.service;

import org.apache.dubbo.springboot.demo.idl.DubboGreeterTriple.GreeterImplBase;
import org.apache.dubbo.springboot.demo.idl.GreeterReply;
import org.apache.dubbo.springboot.demo.idl.GreeterRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class GreeterImpl extends GreeterImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreeterImpl.class);

    @Override
    public GreeterReply greet(GreeterRequest request) {
        LOGGER.info("Server received greet request {}" , request);
        return GreeterReply.newBuilder()
                .setMessage("hello," + request.getName())
                .build();
    }

}
