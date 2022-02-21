package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.protocol.tri.CancelableStreamObserver;
import org.apache.dubbo.rpc.protocol.tri.ClientStreamObserver;
import org.apache.dubbo.sample.tri.helper.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.service.PbGreeterManual;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author earthchen
 * @date 2021/9/9
 **/
public abstract class BasePbConsumerTest {

    protected static PbGreeter delegate;

    protected static PbGreeterManual delegateManual;

    protected static DubboBootstrap appDubboBootstrap;

    @Test
    public void serverStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        StreamObserver<GreeterReply> observer = new StdoutStreamObserver<GreeterReply>("sayGreeterServerStream") {
            @Override
            public void onNext(GreeterReply data) {
                super.onNext(data);
                RpcContext.getCancellationContext().cancel(null);
                latch.countDown();
            }
        };

        delegate.greetServerStream(request, observer);
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }


    @Test
    public void cancelServerStream() throws InterruptedException {
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        int n = 10;
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                System.out.println(data);
                cancel(null);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };
        delegateManual.cancelServerStream(request, observer);
        Thread.sleep(2000);
        GreeterReply reply = delegateManual.queryCancelResult(
                GreeterRequest.newBuilder()
                        .setName("cancelServerStream")
                        .build()
        );
        Assert.assertEquals("true", reply.getMessage());
    }


    @Test
    public void cancelBiStream() throws InterruptedException {
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                System.out.println(data);
                cancel(null);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };
        final StreamObserver<GreeterRequest> requestObserver =
                delegateManual.cancelBiStream(observer);
        CancelableStreamObserver<GreeterRequest> streamObserver =
                (CancelableStreamObserver<GreeterRequest>) requestObserver;
        streamObserver.onNext(request);
        streamObserver.cancel(new RuntimeException());
        streamObserver.onCompleted();
        Thread.sleep(2000);
        GreeterReply reply = delegateManual.queryCancelResult(
                GreeterRequest.newBuilder()
                        .setName("cancelBiStream")
                        .build()
        );
        Assert.assertEquals("true", reply.getMessage());
    }


    @Test
    public void cancelBiStream2() throws InterruptedException {
        int n = 10;
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                System.out.println(data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };
        final StreamObserver<GreeterRequest> requestObserver =
                delegateManual.cancelBiStream2(observer);
        CancelableStreamObserver<GreeterRequest> streamObserver =
                (CancelableStreamObserver<GreeterRequest>) requestObserver;
        for (int i = 0; i < n; i++) {
            streamObserver.onNext(request);
        }
        streamObserver.cancel(null);
        // streamObserver.onCompleted();
        Thread.sleep(2000);
        GreeterReply reply = delegateManual.queryCancelResult(
                GreeterRequest.newBuilder()
                        .setName("cancelBiStream2")
                        .build()
        );
        Assert.assertEquals("true", reply.getMessage());
    }

    @Test
    public void compressorBiStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                System.out.println(data);
                latch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };
        final ClientStreamObserver<GreeterRequest> requestObserver =
                (ClientStreamObserver<GreeterRequest>) delegateManual.compressorBiStream(observer);
        requestObserver.setCompression("gzip");
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }


    @Test
    public void clientCompressorBiStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                System.out.println(data);
                latch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };
        final ClientStreamObserver<GreeterRequest> requestObserver =
                (ClientStreamObserver<GreeterRequest>) delegateManual.clientCompressorBiStream(observer);
        requestObserver.setCompression("gzip");
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void serverCompressorBiStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new CancelableStreamObserver<GreeterReply>() {
            @Override
            public void onNext(GreeterReply data) {
                System.out.println(data);
                latch.countDown();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };
        final ClientStreamObserver<GreeterRequest> requestObserver =
                (ClientStreamObserver<GreeterRequest>) delegateManual.clientCompressorBiStream(observer);
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }


    @Test
    public void stream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("stream request")
                .build();
        StreamObserver<GreeterReply> observer = new StdoutStreamObserver<GreeterReply>("tri pb stream") {
            @Override
            public void onNext(GreeterReply data) {
                super.onNext(data);
                latch.countDown();
            }
        };
        final StreamObserver<GreeterRequest> requestObserver =
                delegate.greetStream(observer);
        for (int i = 0; i < n; i++) {
            requestObserver.onNext(request);
        }
        requestObserver.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void unaryGreeter() {
        final GreeterReply reply = delegate.greet(GreeterRequest.newBuilder()
                .setName("name")
                .build());
        Assert.assertNotNull(reply);
    }


    @Test(expected = RpcException.class)
    public void clientSendLargeSizeHeader() {
        StringBuilder sb = new StringBuilder("a");
        for (int j = 0; j < 15; j++) {
            sb.append(sb);
        }
        sb.setLength(8000);
        RpcContext.getClientAttachment().setObjectAttachment("large-size-meta", sb.toString());
        delegate.greet(GreeterRequest.newBuilder().setName("meta").build());
        RpcContext.getClientAttachment().clearAttachments();
    }


    // @Test(expected = RpcException.class)
    @Test
    @Ignore
    public void serverSendLargeSizeHeader() {
        final String key = "user-attachment";
        GreeterReply reply =
                delegateManual.greetReturnBigAttachment(GreeterRequest.newBuilder().setName("meta").build());
        final String returned = (String) RpcContext.getServerContext().getObjectAttachment(key);
        Assert.assertNotNull(returned);
    }

    @Test
    public void attachmentTest() {
        final String key = "user-attachment";
        final String value = "attachment-value";
        RpcContext.removeClientAttachment();
        RpcContext.getClientAttachment().setAttachment(key, value);
        GreeterReply reply = delegate.greetWithAttachment(GreeterRequest.newBuilder().setName("meta").build());
        Assert.assertEquals("hello,meta", reply.getMessage());
        final String returned = (String) RpcContext.getServerContext().getObjectAttachment(key);
        Assert.assertEquals("hello," + value, returned);
    }

    @Test
    public void attachmentTest2() {
        final String key = "user-attachment";
        final String value = "attachment-value";
        RpcContext.removeClientAttachment();
        RpcContext.getClientAttachment().setAttachment(key, value);
        GreeterReply reply = delegateManual.greetWithAttachment(GreeterRequest.newBuilder().setName("meta").build());
        Assert.assertEquals("hello,meta", reply.getMessage());
        final String returned = (String) RpcContext.getServerContext().getObjectAttachment(key);
        Assert.assertEquals("hello," + value, returned);
    }

    @Test
    public void methodNotFound() {
        try {
            delegateManual.methodNonExist(GreeterRequest.newBuilder().setName("meta").build());
            TimeUnit.SECONDS.sleep(1);
        } catch (RpcException | InterruptedException e) {
            Assert.assertTrue(e.getMessage().contains("not found"));
        }
    }

    @AfterClass
    public static void alterTest() {
        appDubboBootstrap.destroy();
        DubboBootstrap.reset();
    }


}
