package org.apache.dubbo.samples.legacy;

import org.apache.dubbo.common.Version;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.legacy.api.DemoService;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

public class LegacyTest {
    @Test
    public void testFor3_2() {
        if (!Version.getVersion().startsWith("3.2")) {
            return;
        }

        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setLoadbalance("roundrobin");

        DubboBootstrap bootstrap = DubboBootstrap.newInstance()
                .application("demo-client")
                .registry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos"))
                .reference(referenceConfig)
                .start();

        DemoService demoService = referenceConfig.get();
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .anyMatch(s -> s.equals("hello from 2.7.0")));

        bootstrap.stop();
    }

    @Test
    public void testFor3_3() {
        if (!Version.getVersion().startsWith("3.3")) {
            return;
        }

        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setLoadbalance("roundrobin");

        DubboBootstrap bootstrap = DubboBootstrap.newInstance()
                .application("demo-client")
                .registry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos"))
                .reference(referenceConfig)
                .start();

        DemoService demoService = referenceConfig.get();
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .noneMatch(s -> s.equals("hello from 2.7.0")));

        bootstrap.stop();
    }

    @Test
    public void testEnable() {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setLoadbalance("roundrobin");

        DubboBootstrap bootstrap = DubboBootstrap.newInstance()
                .application("demo-client")
                .registry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos&nacos.subscribe.legacy-name=true"))
                .reference(referenceConfig)
                .start();

        DemoService demoService = referenceConfig.get();
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .anyMatch(s -> s.equals("hello from 2.7.0")));

        bootstrap.stop();
    }

    @Test
    public void testDisable() {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setLoadbalance("roundrobin");

        DubboBootstrap bootstrap = DubboBootstrap.newInstance()
                .application("demo-client")
                .registry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos&nacos.subscribe.legacy-name=false"))
                .reference(referenceConfig)
                .start();

        DemoService demoService = referenceConfig.get();
        Assert.assertTrue(IntStream.range(0, 10)
                .mapToObj(i -> demoService.sayHello())
                .noneMatch(s -> s.startsWith("hello from 2")));

        bootstrap.stop();
    }
}
