package org.apache.dubbo.springboot.demo.provider.dubbo.consumer;

import org.apache.dubbo.springboot.demo.idl.Greeter;
import org.apache.dubbo.springboot.demo.idl.GreeterReply;
import org.apache.dubbo.springboot.demo.idl.GreeterRequest;

import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Consumer implements CommandLineRunner {

    @DubboReference
    private Greeter greeter;

    @Override
    public void run(String... args) throws Exception {
        GreeterReply result = greeter.greet(GreeterRequest.newBuilder().setName("name").build());
        System.out.println("Received result ======> " + result.getMessage());
    }
}
