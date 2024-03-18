//package org.apache.dubbo.samples.registry.api;
//
//import org.apache.dubbo.common.logger.Logger;
//import org.apache.dubbo.common.logger.LoggerFactory;
//import org.apache.dubbo.samples.nacos.registry.api.GreetingService;
//
//public class GreetingServiceRegistry implements GreetingService {
//
//    private static Logger logger = LoggerFactory.getLogger(GreetingServiceRegistry.class);
//
//    @Override
//    public String sayHello(String name) {
//        System.out.println("greeting service received: " + name);
//        return "hello, " + name;
//    }
//
//}
