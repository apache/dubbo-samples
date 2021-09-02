package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.hello.HelloReply;
import org.apache.dubbo.hello.HelloRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PbTest {
    private static PbGreeter delegate;

    @BeforeClass
    public static void init() {
        ReferenceConfig<PbGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(PbGreeter.class);
        ref.setCheck(false);
        ref.setInterface(PbGreeter.class);
        ref.setCheck(false);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);
        ref.setTimeout(10000);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-consumer"))
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(ref)
                .start();

        delegate = ref.get();
    }

    @Test
    public void serverStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final HelloRequest request = HelloRequest.newBuilder()
                .setName("request")
                .build();
        delegate.sayHelloServerStream(request, new StdoutStreamObserver<HelloReply>("sayHelloServerStream") {
            @Override
            public void onNext(HelloReply data) {
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
        final HelloRequest request = HelloRequest.newBuilder()
                .setName("stream request")
                .build();
        final StreamObserver<HelloRequest> requestObserver = delegate.sayHelloStream(new StdoutStreamObserver<HelloReply>("sayHelloStream") {
            @Override
            public void onNext(HelloReply data) {
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
    public void unaryHello() {
        final HelloReply reply = delegate.sayHello(HelloRequest.newBuilder()
                .setName("name")
                .build());
        Assert.assertNotNull(reply);
    }

}
