package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;

import java.util.function.Function;

public class Helper {
}


class EchoStreamObserver<T, R> implements StreamObserver<T> {
    private final Function<T, R> echoFunc;
    private final StreamObserver<R> responseObserver;

    EchoStreamObserver(Function<T, R> echoFunc, StreamObserver<R> responseObserver) {
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

class StdoutStreamObserver<T> implements StreamObserver<T> {
    private final String name;

    StdoutStreamObserver(String name) {
        this.name = name;
    }

    @Override
    public void onNext(T data) {
        System.out.println("[" + name + "] stream reply:" + data);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("[" + name + "] Error:");
        throwable.printStackTrace();
    }

    @Override
    public void onCompleted() {
        System.out.println("[" + name + "] stream done");
    }
}
