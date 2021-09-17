package org.apache.dubbo.sample.tri.direct;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.BaseTriWrapConsumerTest;
import org.apache.dubbo.sample.tri.TriSampleConstants;
import org.apache.dubbo.sample.tri.service.WrapGreeter;

import org.junit.BeforeClass;

public class TriDirectWrapConsumerTest extends BaseTriWrapConsumerTest {

    @BeforeClass
    public static void initStub() {
        ReferenceConfig<WrapGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(WrapGreeter.class);
        ref.setCheck(false);
        ref.setTimeout(3000);
        ref.setUrl(TriSampleConstants.DEFAULT_ADDRESS);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);

        ReferenceConfig<WrapGreeter> ref2 = new ReferenceConfig<>();
        ref2.setInterface(WrapGreeter.class);
        ref2.setCheck(false);
        ref2.setTimeout(15000);
        ref2.setUrl(TriSampleConstants.DEFAULT_ADDRESS);
        ref2.setRetries(0);
        ref2.setProtocol(CommonConstants.TRIPLE);
        ref2.setLazy(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(TriDirectWrapConsumerTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        bootstrap.application(applicationConfig)
                .reference(ref)
                .start();
        delegate = ref.get();
        longDelegate = ref2.get();
        appDubboBootstrap = bootstrap;
    }

}
