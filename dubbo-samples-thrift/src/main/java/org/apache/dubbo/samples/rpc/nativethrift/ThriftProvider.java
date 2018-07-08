package org.apache.dubbo.samples.rpc.nativethrift;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ThriftProvider {
    /**
     * thrift provider
     */
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("thrift-provider.xml");
        context.start();
        System.out.println("context started");
        System.in.read();
    }

}
