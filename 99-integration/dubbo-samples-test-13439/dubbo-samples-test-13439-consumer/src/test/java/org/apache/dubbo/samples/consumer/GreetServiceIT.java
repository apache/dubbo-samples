package org.apache.dubbo.samples.consumer;

import org.apache.dubbo.config.MetadataReportConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.api.GreetService;
import org.junit.Assert;
import org.junit.Test;


public class GreetServiceIT {

    @Test
    public void test(){
        System.setProperty("dubbo.application.qos-enable","false");
        RegistryConfig registryConfig = new RegistryConfig(GreetService.NACOS_ADDR);
        registryConfig.setRegisterMode("instance");
        ReferenceConfig<GreetService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(GreetService.class);
        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application("GreetConsumerApplication")
                .registry(registryConfig)
                .metadataReport(new MetadataReportConfig(GreetService.NACOS_NAMESPACE_2_PATH))
                .protocol(new ProtocolConfig("dubbo", GreetService.PORT))
                .reference(referenceConfig);
        bootstrap.start();
        Assert.assertEquals("Hello:world",referenceConfig.get().greet("world"));
    }
}
