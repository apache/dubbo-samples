package org.apache.dubbo.sample.tri.api;

import org.apache.dubbo.common.stream.StreamObserver;

/**
 * Triple supports manual interface with POJO to support migrating from original protocols.
 */
public interface PojoGreeter {

    String overload();

    String overload(String param);

    /**
     * <pre>
     *  Sends a greeting
     * </pre>
     */
    String greetLong(int len);

    /**
     * unary
     */
    String greet(String request);

    /**
     * unary
     */
    void greetResponseVoid(String request);

    /**
     * unary
     */
    String sayGreeterRequestVoid();

    String greetException(String request);

    String greetWithAttachment(String request);

    /**
     * bi stream
     */
    StreamObserver<String> greetStream(StreamObserver<String> response);

    StreamObserver<String> greetStreamError(StreamObserver<String> response);

    /**
     * server stream
     */
    void greetServerStream(String request, StreamObserver<String> response);
}
