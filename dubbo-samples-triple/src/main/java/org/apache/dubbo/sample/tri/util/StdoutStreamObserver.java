package org.apache.dubbo.sample.tri.util;

import org.apache.dubbo.common.stream.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author earthchen
 * @date 2021/9/6
 **/
public class StdoutStreamObserver<T> implements StreamObserver<T>, io.grpc.stub.StreamObserver<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StdoutStreamObserver.class);

    private final String name;

    public StdoutStreamObserver(String name) {
        this.name = name;
    }

    @Override
    public void onNext(T data) {
        LOGGER.info("{} stream <- reply:{}", name, data);
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("{} stream onError", name, throwable);
        throwable.printStackTrace();
    }

    @Override
    public void onCompleted() {
        LOGGER.info("{} stream completed", name);
    }

    public io.grpc.stub.StreamObserver<T> asGrpcObserver() {
        return new GrpcStreamObserverAdapter<>(this);
    }
}