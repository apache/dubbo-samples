package org.apache.dubbo.sample.tri;

import io.grpc.stub.StreamObserver;

public class GrpcPbGreeterImpl extends PbGreeterGrpc.PbGreeterImplBase {
    private final PbGreeter delegate;

    public GrpcPbGreeterImpl(PbGreeter delegate) {
        this.delegate = delegate;
    }

    @Override
    public void greet(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        try {
            final GreeterReply response = delegate.greet(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public void greetException(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        try {
            final GreeterReply response = delegate.greetException(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public StreamObserver<GreeterRequest> greetStream(StreamObserver<GreeterReply> responseObserver) {
        return new GrpcStreamObserverAdapter<>(delegate.greetStream(new StreamObserverAdapter<>(responseObserver)));
    }

    @Override
    public void greetWithAttachment(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        try {
            final GreeterReply response = delegate.greetWithAttachment(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public void greetServerStream(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        delegate.greetServerStream(request, new StreamObserverAdapter<>(responseObserver));
    }
}