package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;

public interface PbGreeter {
    GreeterReply Greet(GreeterRequest request);

    GreeterReply GreetException(GreeterRequest request);

    StreamObserver<GreeterRequest> GreetStream(StreamObserver<GreeterReply> replyStream);

    void GreetServerStream(GreeterRequest request, StreamObserver<GreeterReply> replyStream);
}
