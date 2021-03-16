package com.apache.dubbo.sample.basic;

import org.apache.dubbo.hello.HelloReply;
import org.apache.dubbo.hello.HelloRequest;

public interface IGreeter {
    /**
     * <pre>
     *  Sends a greeting
     * </pre>
     */
    HelloReply sayHello(HelloRequest request);

}
