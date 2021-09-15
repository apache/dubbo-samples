package org.apache.dubbo.sample.tri.helper;


import io.grpc.stub.StreamObserver;

public class GrpcStreamObserverAdapter<T> implements StreamObserver<T> {

    private final org.apache.dubbo.common.stream.StreamObserver<T> delegate;

    public GrpcStreamObserverAdapter(org.apache.dubbo.common.stream.StreamObserver<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onNext(T data) {
        delegate.onNext(data);
    }

    @Override
    public void onError(Throwable throwable) {
        delegate.onError(throwable);
    }

    @Override
    public void onCompleted() {
        delegate.onCompleted();
    }
}
