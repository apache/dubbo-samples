package org.apache.dubbo.sample.tri.stub;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.protocol.tri.ServerStreamObserver;
import org.apache.dubbo.sample.tri.DubboGreeterTriple.GreeterImplBase;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GreeterImpl extends GreeterImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreeterImpl.class);

    private final String serverName;
    public static final Map<String, Boolean> cancelResultMap = new HashMap<>();

    public GreeterImpl(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public GreeterReply greet(GreeterRequest request) {
        LOGGER.info("Server {} received greet request {}", serverName, request);
        return GreeterReply.newBuilder()
                .setMessage("hello," + request.getName())
                .build();
    }

    @Override
    public GreeterReply greetWithAttachment(GreeterRequest request) {
        final String key = "user-attachment";
        final String value = "hello," + RpcContext.getServerAttachment().getAttachment(key);
        RpcContext.getServerContext().setObjectAttachment(key, value);
        return GreeterReply.newBuilder().setMessage("hello," + request.getName()).build();
    }

    @Override
    public GreeterReply greetReturnBigAttachment(GreeterRequest request) {
        final String key = "user-attachment";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8000; i++) {
            stringBuilder.append(i);
        }
        String value = stringBuilder.toString();
        RpcContext.getServerContext().setObjectAttachment(key, value);
        return GreeterReply.newBuilder().setMessage("hello," + request.getName()).build();
    }

    @Override
    public void cancelServerStream(GreeterRequest request,
                                   StreamObserver<GreeterReply> replyStream) {
        RpcContext.getCancellationContext().addListener(context -> {
            LOGGER.info("cancel--cancelServerStream");
            cancelResultMap.put("cancelServerStream", true);
        });
        for (int i = 0; i < 10; i++) {
            replyStream.onNext(GreeterReply.newBuilder()
                    .setMessage(request.getName() + "--" + i)
                    .build());
        }
    }

    @Override
    public StreamObserver<GreeterRequest> cancelBiStream(StreamObserver<GreeterReply> replyStream) {
        RpcContext.getCancellationContext()
                .addListener(context -> {
                    LOGGER.info("cancel--cancelBiStream");
                    cancelResultMap.put("cancelBiStream", true);
                });
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                LOGGER.info("Bi-Stream-Request:" + data.getName());
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
                // replyStream.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<GreeterRequest> cancelBiStream2(
            StreamObserver<GreeterReply> replyStream) {
        RpcContext.getCancellationContext()
                .addListener(context -> {
                    LOGGER.info("cancel--cancelBiStream2");
                    cancelResultMap.put("cancelBiStream2", true);
                });
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                LOGGER.info("Bi-Stream-Request:" + data.getName());
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
                // replyStream.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<GreeterRequest> compressorBiStream(
            StreamObserver<GreeterReply> replyStream) {
        ServerStreamObserver<GreeterReply> replyServerStreamObserver = (ServerStreamObserver<GreeterReply>) replyStream;
        replyServerStreamObserver.setCompression("gzip");
        return getGreeterRequestStreamObserver(replyServerStreamObserver);
    }

    private StreamObserver<GreeterRequest> getGreeterRequestStreamObserver(
            StreamObserver<GreeterReply> streamObserver) {
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                streamObserver.onNext(GreeterReply.newBuilder()
                        .setMessage(data.getName())
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                streamObserver.onError(new IllegalStateException("Stream err"));
            }

            @Override
            public void onCompleted() {
                streamObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<GreeterRequest> clientCompressorBiStream(
            StreamObserver<GreeterReply> replyStream) {
        ServerStreamObserver<GreeterReply> replyServerStreamObserver = (ServerStreamObserver<GreeterReply>) replyStream;
        return getGreeterRequestStreamObserver(replyServerStreamObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> serverCompressorBiStream(
            StreamObserver<GreeterReply> replyStream) {
        ServerStreamObserver<GreeterReply> replyServerStreamObserver = (ServerStreamObserver<GreeterReply>) replyStream;
        replyServerStreamObserver.setCompression("gzip");
        return getGreeterRequestStreamObserver(replyServerStreamObserver);
    }

    @Override
    public GreeterReply queryCancelResult(GreeterRequest request) {
        boolean canceled = cancelResultMap.getOrDefault(request.getName(), false);
        return GreeterReply.newBuilder()
                .setMessage(String.valueOf(canceled))
                .build();
    }


    public GreeterReply greetException(GreeterRequest request) {
        RpcContext.getServerContext().setAttachment("str", "str")
                .setAttachment("integer", 1)
                .setAttachment("raw", new byte[]{1, 2, 3, 4});
        throw new RuntimeException("Biz Exception");
    }

    @Override
    public StreamObserver<GreeterRequest> greetStream(StreamObserver<GreeterReply> replyStream) {
        return new StreamObserver<GreeterRequest>() {
            int n = 0;

            @Override
            public void onNext(GreeterRequest data) {
                n++;
                LOGGER.info(data.getName() + " " + n);
                replyStream.onNext(GreeterReply.newBuilder()
                        .setMessage(data.getName() + " " + n)
                        .build());
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                replyStream.onError(new IllegalStateException("Stream err"));
            }

            @Override
            public void onCompleted() {
                LOGGER.info("[greetStream] onCompleted");
                replyStream.onCompleted();
            }
        };
    }

    @Override
    public void greetServerStream(GreeterRequest request,
                                  StreamObserver<GreeterReply> replyStream) {
        for (int i = 0; i < 10; i++) {
            replyStream.onNext(GreeterReply.newBuilder()
                    .setMessage(request.getName() + "--" + i)
                    .build());
        }
        replyStream.onCompleted();
    }

    @Override
    public StreamObserver<org.apache.dubbo.sample.tri.GreeterRequest> greetClientStream(
            StreamObserver<org.apache.dubbo.sample.tri.GreeterReply> responseObserver) {
        return new StreamObserver<GreeterRequest>() {
            private StringBuilder sb = new StringBuilder();

            @Override
            public void onNext(GreeterRequest data) {
                sb.append("hello, ").append(data.getName()).append("\n");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(GreeterReply.newBuilder()
                        .setMessage(sb.toString())
                        .build());
                responseObserver.onCompleted();
            }
        };
    }
}
