package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.generic.GenericClient;
import org.apache.dubbo.sample.tri.util.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TriFlowcontrolClientTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(GenericClient.class);

    private static PojoGreeter delegate;

    protected static PojoGreeter longDelegate;

    protected static DubboBootstrap appDubboBootstrap;

    @BeforeClass
    public static void initStub() {
        ReferenceConfig<PojoGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(PojoGreeter.class);
        ref.setTimeout(3000);
        ref.setRetries(0);
        ref.setProtocol(CommonConstants.TRIPLE);

        ReferenceConfig<PojoGreeter> ref2 = new ReferenceConfig<>();
        ref2.setInterface(PojoGreeter.class);
        ref2.setTimeout(15000);
        ref2.setRetries(0);
        ref2.setProtocol(CommonConstants.TRIPLE);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(TriPojoClientTest.class.getName());
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
    public void greetStreamFlowControl() throws InterruptedException {
        int n = 10000;
        CountDownLatch latch = new CountDownLatch(1310);
        final StreamObserver<String> request = delegate.greetStream(new StdoutStreamObserver<String>(
                "greetStream") {
            @Override
            public void onNext(String data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        for (int i = 0; i < 10000; i++) {
            request.onNext("stream request");
        }
        request.onCompleted();
        Assert.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }
}
