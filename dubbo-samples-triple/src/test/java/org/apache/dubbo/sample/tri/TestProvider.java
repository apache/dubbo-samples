package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;


public class TestProvider {
    public static void main(String[] args) {
        ServiceConfig<PbGreeter> pbService = new ServiceConfig<>();
        pbService.setInterface(PbGreeter.class);
        pbService.setRef(new PbGreeterImpl());

        ServiceConfig<WrapGreeter> wrapService = new ServiceConfig<>();
        wrapService.setInterface(WrapGreeter.class);
        wrapService.setRef(new WrapGreeterImpl());


        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-provider"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, TriSampleConstants.SERVER_POINT))
                .service(pbService)
                .service(wrapService)
                .start()
                .await();
    }
}
