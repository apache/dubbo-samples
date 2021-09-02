package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;

public interface PbGreeter {
    GreeterReply greetWithAttachment(GreeterRequest request);

    GreeterReply greet(GreeterRequest request);

    GreeterReply methodNonExist(GreeterRequest request);

    GreeterReply greetException(GreeterRequest request);

    StreamObserver<GreeterRequest> greetStream(StreamObserver<GreeterReply> replyStream);

    void greetServerStream(GreeterRequest request, StreamObserver<GreeterReply> replyStream);
}
