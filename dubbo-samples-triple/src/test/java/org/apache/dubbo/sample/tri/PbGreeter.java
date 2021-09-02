package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;

public interface PbGreeter {
    GreeterReply sayGreeter(GreeterRequest request);

    GreeterReply sayGreeterException(GreeterRequest request);

    StreamObserver<GreeterRequest> sayGreeterStream(StreamObserver<GreeterReply> replyStream);

    void sayGreeterServerStream(GreeterRequest request, StreamObserver<GreeterReply> replyStream);
}
