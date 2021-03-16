package com.apache.dubbo.sample.basic;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import java.util.concurrent.CountDownLatch;

public class ApiProvider {
    public static void main(String[] args) throws InterruptedException {
        ServiceConfig<IGreeter> service = new ServiceConfig<>();
        service.setInterface(IGreeter.class);
        service.setRef(new IGreeter1Impl());
        service.setProtocol(new ProtocolConfig(CommonConstants.TRIPLE, 50051));
        service.setApplication(new ApplicationConfig("demo-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        service.export();
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
