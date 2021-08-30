package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.hello.HelloReply;
import org.apache.dubbo.hello.HelloRequest;

public interface PbGreeter {
    HelloReply sayHello(HelloRequest request);

    HelloReply sayHelloException(HelloRequest request);

    StreamObserver<HelloRequest> sayHelloStream(StreamObserver<HelloReply> replyStream);

    void sayHelloServerStream(HelloRequest request, StreamObserver<HelloReply> replyStream);
}
