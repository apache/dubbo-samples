package com.apache.dubbo.sample.testing;

import io.grpc.testing.integration.EmptyProtos;
import io.grpc.testing.integration.Messages;
import io.grpc.testing.integration.Messages.SimpleRequest;
import io.grpc.testing.integration.Messages.SimpleResponse;
import io.grpc.testing.integration.Messages.StreamingInputCallRequest;
import io.grpc.testing.integration.Messages.StreamingInputCallResponse;
import org.apache.dubbo.common.stream.StreamObserver;

public interface ITestService {
    EmptyProtos.Empty emptyCall(EmptyProtos.Empty request);

    SimpleResponse unaryCall(SimpleRequest request);
    Messages.SimpleResponse cacheableUnaryCall(Messages.SimpleRequest req);

    //StreamingOutputCallResponse streamingOutputCall(StreamingOutputCallRequest request);

    StreamObserver<StreamingInputCallRequest> streamingInputCall(StreamObserver<StreamingInputCallResponse> responseObserver);

    StreamObserver<Messages.StreamingOutputCallRequest> fullDuplexCall(
        StreamObserver<Messages.StreamingOutputCallResponse> responseObserver);

}
