package org.apache.dubbo.samples.legacy;

import org.apache.dubbo.samples.legacy.api.DemoService;
import org.apache.dubbo.samples.legacy.impl.DemoServiceImpl;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

import java.util.concurrent.CountDownLatch;

public class Provider {
    public static void main(String[] args) throws InterruptedException {
        ServiceConfig<DemoService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(DemoService.class);
        serviceConfig.setRef(new DemoServiceImpl());
        serviceConfig.setApplication(new ApplicationConfig("demo"));
        serviceConfig.setRegistry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos"));
        serviceConfig.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
