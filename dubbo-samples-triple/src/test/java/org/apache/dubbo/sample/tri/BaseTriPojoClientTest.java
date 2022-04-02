package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.sample.tri.util.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.api.PojoGreeter;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class BaseTriPojoClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTriPojoClientTest.class);


    protected static PojoGreeter delegate;

    protected static PojoGreeter longDelegate;

    protected static DubboBootstrap appDubboBootstrap;

    @Test
    public void overload() {
        String ret = delegate.overload();
        Assert.assertEquals(ret, "overload");
        String ret2 = delegate.overload("overload");
        Assert.assertEquals(ret2, "overload");
    }

    @Test
    public void greetUnaryRequestVoid() {
        Assert.assertEquals("hello!void", delegate.sayGreeterRequestVoid());
    }

    @Test
    public void greetUnaryResponseVoid() {
        delegate.greetResponseVoid("void");
    }

    @Test
    public void greetUnary() {
        Assert.assertEquals("hello,unary", delegate.greet("unary"));
    }

    @Test(expected = RpcException.class)
    public void greetException() {
        delegate.greetException("exception");
    }

    @Test
    public void greetServerStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        delegate.greetServerStream("server stream", new StdoutStreamObserver<String>("greetServerStream") {
            @Override
            public void onNext(String data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void greetStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final StreamObserver<String> request = delegate.greetStream(new StdoutStreamObserver<String>(
                "greetStream") {
            @Override
            public void onNext(String data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        for (int i = 0; i < n; i++) {
            request.onNext("stream request");
        }
        request.onCompleted();
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void greetLong() {
        int power = 25;
        for (int i = 0; i < power; i++) {
            final int len = (1 << i);
            final String response = longDelegate.greetLong(len);
            LOGGER.info("Response len:" + response.length());
            Assert.assertEquals(len, response.length());
        }
    }


    @AfterClass
    public static void alterTest() {
        appDubboBootstrap.stop();
        DubboBootstrap.reset();
    }
}
