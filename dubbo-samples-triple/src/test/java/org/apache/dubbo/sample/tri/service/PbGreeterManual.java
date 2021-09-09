package org.apache.dubbo.sample.tri.service;

import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;

/**
 * this is by manual and other by dubbo compiler
 */
public interface PbGreeterManual {

    GreeterReply greetWithAttachment(GreeterRequest request);
//
//    GreeterReply greet(GreeterRequest request);

    GreeterReply methodNonExist(GreeterRequest request);
//
//    GreeterReply greetException(GreeterRequest request);
//
//    StreamObserver<GreeterRequest> greetStream(StreamObserver<GreeterReply> replyStream);
//
//    void greetServerStream(GreeterRequest request, StreamObserver<GreeterReply> replyStream);
}
