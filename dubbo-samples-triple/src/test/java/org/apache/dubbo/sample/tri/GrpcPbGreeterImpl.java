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
            final GreeterReply response = delegate.Greet(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public void greetException(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        try {
            final GreeterReply response = delegate.GreetException(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Throwable t) {
            responseObserver.onError(t);
        }
    }

    @Override
    public StreamObserver<GreeterRequest> greetStream(StreamObserver<GreeterReply> responseObserver) {
        return new GrpcStreamObserverAdapter<>(delegate.GreetStream(new StreamObserverAdapter<>(responseObserver)));
    }

    @Override
    public void greetServerStream(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        delegate.GreetServerStream(request, new StreamObserverAdapter<>(responseObserver));
    }
}