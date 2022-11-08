package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import org.apache.dubbo.sample.tri.util.TriSampleConstants;
import org.junit.BeforeClass;

public class TriStubClientTest extends BaseClientTest {

    @BeforeClass
    public static void init() {
        ReferenceConfig<Greeter> ref = new ReferenceConfig<>();
        ref.setInterface(Greeter.class);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setTimeout(10000);
        ref.setProxy(CommonConstants.NATIVE_STUB);
        ref.setRetries(0);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(
            TriStubClientTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        bootstrap.application(applicationConfig)
            .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
            .reference(ref)
            .start();

        delegate = ref.get();
        appDubboBootstrap = bootstrap;
    }
}
