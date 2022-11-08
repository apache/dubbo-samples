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


    static class Client implements Runnable {
        private String protocol;
        private String mode = "APPLICATION_FIRST";
        Client(String protocol){
            this.protocol = protocol;
        }

        Client(String protocol, String mode) {
            this(protocol);
            this.mode = mode;
        }

        @Override
        public void run() {
            ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();
            reference.setInterface(GreetingService.class);
            reference.setParameters(new HashMap<>());
            reference.getParameters().put("migration.step", mode);
            reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
            reference.setRegistry(new RegistryConfig(
                    "zookeeper://" + "127.0.0.1" + ":" + "2181"));
            reference.setProtocol(this.protocol);
            GreetingService service = reference.get();
            String message = service.sayHi(this.protocol);
            System.out.println(message);
            Assert.assertEquals("hi, tri",message);
        }
    }

    @Test
    public void testInterfaceDiscovery() throws InterruptedException {
        DemoClientIT.Client dubboClient = new DemoClientIT.Client(CommonConstants.DUBBO);
        DemoClientIT.Client triClient = new DemoClientIT.Client(CommonConstants.TRIPLE, "FORCE_INTERFACE");
        Thread t1 = new Thread(dubboClient);
        t1.start();
        Thread t2 = new Thread(triClient);
        t2.start();
        t1.join();
        t2.join();
    }


}
