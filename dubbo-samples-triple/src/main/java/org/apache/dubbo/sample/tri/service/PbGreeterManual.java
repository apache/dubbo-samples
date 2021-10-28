package org.apache.dubbo.sample.tri.service;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;

/**
 * this is by manual and other by dubbo compiler
 */
public interface PbGreeterManual {

    GreeterReply greetWithAttachment(GreeterRequest request);

    GreeterReply greetReturnBigAttachment(GreeterRequest request);

    void cancelServerStream(GreeterRequest request, StreamObserver<GreeterReply> replyStream);

    StreamObserver<GreeterRequest> cancelBiStream(StreamObserver<GreeterReply> replyStream);


    StreamObserver<GreeterRequest> cancelBiStream2(StreamObserver<GreeterReply> replyStream);


    StreamObserver<GreeterRequest> compressorBiStream(StreamObserver<GreeterReply> replyStream);


    StreamObserver<GreeterRequest> clientCompressorBiStream(StreamObserver<GreeterReply> replyStream);


    StreamObserver<GreeterRequest> serverCompressorBiStream(StreamObserver<GreeterReply> replyStream);

    /**
     * only use by query cancel result
     *
     * @param request
     * @return
     */
    GreeterReply queryCancelResult(GreeterRequest request);


//
//    GreeterReply greet(GreeterRequest request);

    GreeterReply methodNonExist(GreeterRequest request);
//
//    GreeterReply greetException(GreeterRequest request);
//
//    StreamObserver<GreeterRequest> greetStream(StreamObserver<GreeterReply> replyStream);
//
}
