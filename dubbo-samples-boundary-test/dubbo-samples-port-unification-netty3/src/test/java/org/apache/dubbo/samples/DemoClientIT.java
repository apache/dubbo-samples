package org.apache.dubbo.samples;


import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.samples.api.GreetingService;
import org.apache.dubbo.samples.consumer.Application;

import javassist.runtime.Inner;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class DemoClientIT {

    @Test
    public void testInterfaceDiscovery() throws InterruptedException {
        ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();
        reference.setInterface(GreetingService.class);
        reference.setParameters(new HashMap<>());
        reference.getParameters().put("migration.step", "FORCE_INTERFACE");
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig(
                "zookeeper://" + "127.0.0.1" + ":" + "2181"));
        reference.setProtocol(CommonConstants.TRIPLE);
        GreetingService service = reference.get();
        String message = service.sayHi(CommonConstants.TRIPLE);
        System.out.println(message);
        Assert.assertEquals("hi, tri",message);

    }


}
