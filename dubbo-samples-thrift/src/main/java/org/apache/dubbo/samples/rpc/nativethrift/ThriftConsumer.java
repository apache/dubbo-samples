package org.apache.dubbo.samples.rpc.nativethrift;

import com.alibaba.dubbo.rpc.RpcContext;

import org.apache.dubbo.samples.rpc.nativethrift.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ThriftConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("thrift-consumer.xml");
        context.start();
        DemoService.Iface demo = (DemoService.Iface) context.getBean("demoService");
        for (int i = 0; i < 10; i++) {
            RpcContext.getContext().setAttachment("parm", "hehe"+i);
            System.out.println(demo.echoI32(i + 1));
            System.out.println(demo.echoBool(true));
        }
        context.close();
    }
}