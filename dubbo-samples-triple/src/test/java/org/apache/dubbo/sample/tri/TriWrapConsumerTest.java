package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.sample.tri.helper.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.service.WrapGreeter;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TriWrapConsumerTest {

    private static WrapGreeter delegate;

    protected static WrapGreeter longDelegate;

    protected static DubboBootstrap appDubboBootstrap;

    @BeforeClass
    public static void initStub() {
        ReferenceConfig<WrapGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(WrapGreeter.class);
        ref.setCheck(false);
        ref.setTimeout(3000);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);


        ReferenceConfig<WrapGreeter> ref2 = new ReferenceConfig<>();
        ref2.setInterface(WrapGreeter.class);
        ref2.setCheck(false);
        ref2.setTimeout(15000);
        ref2.setRetries(0);
        ref2.setProtocol(CommonConstants.TRIPLE);
        ref2.setLazy(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(TriWrapConsumerTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        bootstrap.application(applicationConfig)
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref)
                .reference(ref2)
                .start();
        delegate = ref.get();
        longDelegate = ref2.get();
        appDubboBootstrap = bootstrap;

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


        delegate.sayHelloServerStream("server stream", new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
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
        });


        StreamObserver<String> request = delegate.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
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
        });
        for (int i = 0; i < n; i++) {
            request.onNext("stream request" + i);
        }
        request.onCompleted();


    }

    @Test
    public void sayHelloStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final StreamObserver<String> request = delegate.sayHelloStream(new StdoutStreamObserver<String>(
                "sayHelloStream") {
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
            final String response = longDelegate.sayHelloLong(len);
            System.out.println("Response len:" + response.length());
            Assert.assertEquals(len, response.length());
        }
    }

    @AfterClass
    public static void alterTest() {
        appDubboBootstrap.stop();
        DubboBootstrap.reset();
    }
}
