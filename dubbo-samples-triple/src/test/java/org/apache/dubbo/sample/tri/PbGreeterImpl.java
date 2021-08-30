package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.hello.HelloReply;
import org.apache.dubbo.hello.HelloRequest;
import org.apache.dubbo.rpc.RpcContext;

public class PbGreeterImpl implements PbGreeter {
    @Override
    public HelloReply sayHello(HelloRequest request) {

        return HelloReply.newBuilder()
                .setMessage(request.getName())
                .build();
    }

    public HelloReply sayHelloException(HelloRequest request) {
        RpcContext.getServerContext().setAttachment("str", "str")
                .setAttachment("integer", 1)
                .setAttachment("raw", new byte[]{1, 2, 3, 4});
        throw new RuntimeException("Biz Exception");
    }

    @Override
    public StreamObserver<HelloRequest> sayHelloStream(StreamObserver<HelloReply> replyStream) {
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest data) {
                replyStream.onNext(HelloReply.newBuilder()
                        .setMessage(data.getName())
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                replyStream.onError(new IllegalStateException("Stream err"));
            }

            @Override
            public void onCompleted() {
                replyStream.onCompleted();
            }
        };
    }

    @Override
    public void sayHelloServerStream(HelloRequest request, StreamObserver<HelloReply> replyStream) {
        for (int i = 0; i < 10; i++) {
            replyStream.onNext(HelloReply.newBuilder()
                    .setMessage(request.getName())
                    .build());
        }
        replyStream.onCompleted();
    }
}