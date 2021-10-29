package org.apache.dubbo.sample.tri.service;

import org.apache.dubbo.common.stream.StreamObserver;

public interface WrapGreeter {


    String overload();


    String overload(String param);
    /**
     * <pre>
     *  Sends a greeting
     * </pre>
     */
    String sayHelloLong(int len);

    /**
     * unray
     */
    String sayHello(String request);

    /**
     * unray
     */
    void sayHelloResponseVoid(String request);

    /**
     * unray
     */
    String sayHelloRequestVoid();

    String sayHelloException(String request);

    String sayHelloWithAttachment(String request);

    /**
     * bi stream
     */
    StreamObserver<String> sayHelloStream(StreamObserver<String> response);

    StreamObserver<String> sayHelloStreamError(StreamObserver<String> response);

    /**
     * server stream
     */
    void sayHelloServerStream(String request, StreamObserver<String> response);
}
