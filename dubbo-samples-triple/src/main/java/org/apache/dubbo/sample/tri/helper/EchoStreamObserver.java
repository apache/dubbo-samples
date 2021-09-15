package org.apache.dubbo.sample.tri.helper;

import org.apache.dubbo.common.stream.StreamObserver;

import java.util.function.Function;

/**
 * @author earthchen
 * @date 2021/9/6
 **/
public class EchoStreamObserver<T, R> implements StreamObserver<T> {

    private final Function<T, R> echoFunc;
    private final StreamObserver<R> responseObserver;

    public EchoStreamObserver(Function<T, R> echoFunc, StreamObserver<R> responseObserver) {
        this.echoFunc = echoFunc;
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(T data) {
        responseObserver.onNext(echoFunc.apply(data));
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        responseObserver.onError(new IllegalStateException("Stream err"));
    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
    }
}
