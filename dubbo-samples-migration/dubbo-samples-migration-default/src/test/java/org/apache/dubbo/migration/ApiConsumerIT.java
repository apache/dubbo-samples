package org.apache.dubbo.migration;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.migration.pojo.HelloReply;
import org.apache.dubbo.migration.pojo.HelloRequest;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author plusman
 * @since 2021/9/12 11:52 AM
 */
public class ApiConsumerIT {
    @Test
    public void consumeInvoke() {
        String curProtocol = System.getProperty("dubbo.current.protocol", "tri");
        String zookeeperAddress = System.getProperty("zookeeper.address", "127.0.0.1");
        
        ReferenceConfig<GreeterService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(GreeterService.class);
        referenceConfig.setCheck(false);
        referenceConfig.setProtocol(curProtocol);
        referenceConfig.setLazy(true);
        referenceConfig.setTimeout(100000);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("dubbo-demo-triple-api-consumer"))
                .registry(new RegistryConfig("zookeeper://" + zookeeperAddress + ":2181"))
                .reference(referenceConfig)
                .start();

        GreeterService greeterService = referenceConfig.get();
        HelloRequest helloRequest = new HelloRequest();
        helloRequest.setName(curProtocol);
        final HelloReply reply = greeterService.sayHello(helloRequest);
        
        Assert.assertEquals("Hello " + curProtocol, reply.getMessage());
    }
}
