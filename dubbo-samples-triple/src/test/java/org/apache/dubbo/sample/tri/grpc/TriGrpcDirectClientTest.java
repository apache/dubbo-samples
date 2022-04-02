package org.apache.dubbo.sample.tri.grpc;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.BaseClientTest;
import org.apache.dubbo.sample.tri.Greeter;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import org.junit.BeforeClass;

public class TriGrpcDirectClientTest extends BaseClientTest {

    @BeforeClass
    public static void init() {
        ReferenceConfig<Greeter> ref = new ReferenceConfig<>();
        ref.setInterface(Greeter.class);
        ref.setUrl(TriSampleConstants.GRPC_DIRECT_URL);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setTimeout(3000);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(TriGrpcDirectClientTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        bootstrap.application(applicationConfig)
                .reference(ref)
                .start();
        delegate = ref.get();
        appDubboBootstrap=bootstrap;
    }
}
