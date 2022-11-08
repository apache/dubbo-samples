package org.apache.dubbo.sample.tri.inter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.BaseClientTest;
import org.apache.dubbo.sample.tri.Greeter;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import org.junit.BeforeClass;

public class TriInterfaceClientTest extends BaseClientTest {

    @BeforeClass
    public static void init() {
        ReferenceConfig<Greeter> ref = new ReferenceConfig<>();
        ref.setInterface(Greeter.class);
        ref.setCheck(false);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);
        ref.setTimeout(10000);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(TriInterfaceClientTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        bootstrap.application(applicationConfig)
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS_MODE_INTERFACE))
                .reference(ref)
                .start();
        delegate = ref.get();
        appDubboBootstrap=bootstrap;
    }
}
