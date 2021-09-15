package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.sample.tri.helper.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.service.PbGreeterManual;

import org.junit.AfterClass;
import org.junit.Assert;
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

    @Test
    public void serverStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final GreeterRequest request = GreeterRequest.newBuilder()
                .setName("request")
                .build();
        delegate.greetServerStream(request, new StdoutStreamObserver<GreeterReply>("sayGreeterServerStream") {
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
        final StreamObserver<GreeterRequest> requestObserver = delegate.greetStream(new StdoutStreamObserver<GreeterReply>("sayGreeterStream") {
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


    @Test(expected = RpcException.class)
    public void serverSendLargeSizeHeader() {
        final String key = "user-attachment";
        GreeterReply reply = delegateManual.greetReturnBigAttachment(GreeterRequest.newBuilder().setName("meta").build());
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
        Assert.assertEquals("hello,meta",reply.getMessage());
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
        Assert.assertEquals("hello,meta",reply.getMessage());
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
        DubboBootstrap.reset();
    }


}
