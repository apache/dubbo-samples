package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WrapConsumerTest {
    private static WrapGreeter delegate;

    @BeforeClass
    public static void initStub() {
        ReferenceConfig<WrapGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(WrapGreeter.class);
        ref.setCheck(false);
        ref.setTimeout(3000);
        ref.setProtocol("tri");
        ref.setLazy(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-consumer"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref)
                .start();
        delegate = ref.get();
    }

    @Test
    public void sayHelloUnaryRequestVoid() {
        Assert.assertEquals("hello!void", delegate.sayHelloRequestVoid());
    }

    @Test
    public void sayHelloUnaryResponseVoid() {
        delegate.sayHelloResponseVoid("void");
    }

    @Test
    public void sayHelloUnary() {
        Assert.assertEquals("hello,unary", delegate.sayHello("unary"));
    }

    @Test(expected = RpcException.class)
    public void sayHelloException() {
        delegate.sayHelloException("exception");
    }

    @Test
    public void sayHelloServerStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        delegate.sayHelloServerStream("server stream", new StdoutStreamObserver<String>("sayHelloServerStream") {
            @Override
            public void onNext(String data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void sayHelloStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final StreamObserver<String> request = delegate.sayHelloStream(new StdoutStreamObserver<String>("sayHelloStream") {
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
    public void sayHelloLong() {
        int power = 25;
        for (int i = 0; i < power; i++) {
            final int len = (1 << i);
            final String response = delegate.sayHelloLong(len);
            System.out.println("Response len:" + response.length());
            Assert.assertEquals(len, response.length());
        }
    }
}
