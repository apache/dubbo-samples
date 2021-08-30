package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;

public interface WrapGreeter {
    /**
     * <pre>
     *  Sends a greeting
     * </pre>
     */
    String sayHelloLong(int len);

    String sayHello(String request);

    void sayHelloResponseVoid(String request);

    String sayHelloRequestVoid();

    String sayHelloException(String request);

    String sayHelloWithAttachment(String request);

    StreamObserver<String> sayHelloStream(StreamObserver<String> response);

    StreamObserver<String> sayHelloStreamError(StreamObserver<String> response);

    void sayHelloServerStream(String request, StreamObserver<String> response);
}
