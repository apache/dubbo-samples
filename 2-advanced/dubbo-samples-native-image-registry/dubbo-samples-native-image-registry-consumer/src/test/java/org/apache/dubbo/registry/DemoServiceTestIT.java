package org.apache.dubbo.registry;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.registry.consumer.NativeDemoConsumerRegistryApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * if not use SpringBootTest annotation you have to config proxy-config.json and serialization-config.json Manually
 */
@SpringBootTest(classes = {NativeDemoConsumerRegistryApplication.class})
@ExtendWith(SpringExtension.class)
class DemoServiceTestIT {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    @Test
    public void sayHello() {
        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(DemoService.class);
        DemoService service = reference.get();
        HelloResponse message = service.sayHello(new HelloRequest("dubbo"));
        Assertions.assertEquals("dubbo", message.getResponse());
    }

}
