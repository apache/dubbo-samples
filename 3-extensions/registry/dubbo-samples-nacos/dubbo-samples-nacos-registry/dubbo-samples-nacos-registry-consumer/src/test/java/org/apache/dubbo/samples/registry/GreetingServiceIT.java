package org.apache.dubbo.samples.registry;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.nacos.registry.api.GreetingService;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GreetingServiceIT {
    @DubboReference(registry = "true")
    private GreetingService greetingService;

    @Test
    public void testRegistry() {
        String response = greetingService.sayHello("world");
        assertThat(response, is("nacos world"));
    }

}
