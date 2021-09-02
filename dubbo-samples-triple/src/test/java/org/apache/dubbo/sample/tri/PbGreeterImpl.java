package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.rpc.RpcContext;

public class PbGreeterImpl implements PbGreeter {
    @Override
    public GreeterReply Greet(GreeterRequest request) {

        return GreeterReply.newBuilder()
                .setMessage(request.getName())
                .build();
    }

    public GreeterReply GreetException(GreeterRequest request) {
        RpcContext.getServerContext().setAttachment("str", "str")
                .setAttachment("integer", 1)
                .setAttachment("raw", new byte[]{1, 2, 3, 4});
        throw new RuntimeException("Biz Exception");
    }

    @Override
    public StreamObserver<GreeterRequest> GreetStream(StreamObserver<GreeterReply> replyStream) {
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                replyStream.onNext(GreeterReply.newBuilder()
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
    public void GreetServerStream(GreeterRequest request, StreamObserver<GreeterReply> replyStream) {
        for (int i = 0; i < 10; i++) {
            replyStream.onNext(GreeterReply.newBuilder()
                    .setMessage(request.getName())
                    .build());
        }
        replyStream.onCompleted();
    }
}