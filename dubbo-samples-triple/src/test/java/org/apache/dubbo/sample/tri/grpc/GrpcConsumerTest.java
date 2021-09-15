package org.apache.dubbo.sample.tri.grpc;

import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.apache.dubbo.sample.tri.PbGreeterGrpc;
import org.apache.dubbo.sample.tri.helper.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.TriSampleConstants;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GrpcConsumerTest {
    private static PbGreeterGrpc.PbGreeterStub stub;
    private static PbGreeterGrpc.PbGreeterBlockingStub blockingStub;

    @BeforeClass
    public static void init() {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(TriSampleConstants.HOST, TriSampleConstants.GRPC_SERVER_PORT)
                .usePlaintext()
                .build();
        stub = PbGreeterGrpc.newStub(channel);
        blockingStub = PbGreeterGrpc.newBlockingStub(channel);
    }

    @Test
    public void clientSendLargeSizeHeader() throws InterruptedException {
        final Metadata.Key<String> key = Metadata.Key.of("large_size", Metadata.ASCII_STRING_MARSHALLER);
        StringBuilder sb = new StringBuilder("a");
        for (int j = 0; j < 15; j++) {
            sb.append(sb);
        }
        Metadata meta = new Metadata();
        meta.put(key, sb.toString());
        final PbGreeterGrpc.PbGreeterStub curStub = MetadataUtils.attachHeaders(GrpcConsumerTest.stub, meta);
        curStub.greet(GreeterRequest.newBuilder().setName("metadata").build(), new StdoutStreamObserver<>("meta"));
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void serverStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        stub.greetServerStream(request, new StdoutStreamObserver<GreeterReply>("grpc sayGreeterServerStream") {
            @Override
            public void onNext(GreeterReply data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }


    @Test
    public void stream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        final io.grpc.stub.StreamObserver<GreeterRequest> requestObserver = stub.greetStream(new StdoutStreamObserver<GreeterReply>("sayGreeterStream") {
            @Override
            public void onNext(GreeterReply data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void unaryGreeter() {
        final GreeterReply reply = blockingStub.greet(GreeterRequest.newBuilder()
                .setName("name")
                .build());
        Assert.assertNotNull(reply);
    }


    @Test
    public void attachmentTest() {
        final Metadata.Key<String> key = Metadata.Key.of("large_size", Metadata.ASCII_STRING_MARSHALLER);
        Metadata meta = new Metadata();
        meta.put(key, "test");
        final PbGreeterGrpc.PbGreeterBlockingStub curStub = MetadataUtils.attachHeaders(GrpcConsumerTest.blockingStub, meta);
        GreeterReply reply = curStub.greetWithAttachment(GreeterRequest.newBuilder().setName("meta").build());
        Assert.assertEquals("hello,meta", reply.getMessage());
    }

}

