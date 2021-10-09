package org.apache.dubbo.sample.tri.helper;

import org.apache.dubbo.common.stream.StreamObserver;

public class StreamObserverAdapter<T> implements StreamObserver<T> {
    private final io.grpc.stub.StreamObserver<T> delegate;

    public StreamObserverAdapter(io.grpc.stub.StreamObserver<T> delegate) {
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
