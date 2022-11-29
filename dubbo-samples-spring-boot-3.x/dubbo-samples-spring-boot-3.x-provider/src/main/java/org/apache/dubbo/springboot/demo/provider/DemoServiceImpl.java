package org.apache.dubbo.springboot.demo.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.springboot.demo.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;


/**
 *
 * @author <a href="changningbo1995@gmail.com">James</a>
 * @since 2022/11/13
 */
@DubboService
public class DemoServiceImpl  implements DemoService {

    public static final Logger LOGGER = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        LOGGER.info("Hello " + name + ", request from consumer: " + RpcContext.getServiceContext().getRemoteAddress());
        return "Hello " + name;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        LOGGER.info("Say hello async " + name + ", request from consumer: " + RpcContext.getServiceContext().getRemoteAddress());
        return DemoService.super.sayHelloAsync(name);
    }
}
