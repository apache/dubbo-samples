package org.apache.dubbo.sample.tri.helper;

import org.apache.dubbo.common.stream.StreamObserver;

/**
 * @author earthchen
 * @date 2021/9/6
 **/
public class StdoutStreamObserver<T> implements StreamObserver<T>, io.grpc.stub.StreamObserver<T> {


    private final String name;

    public StdoutStreamObserver(String name) {
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