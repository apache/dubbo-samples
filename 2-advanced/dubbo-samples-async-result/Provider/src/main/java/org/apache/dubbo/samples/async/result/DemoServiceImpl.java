package org.apache.dubbo.samples.async.result.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.async.result.api.DemoService;

@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        // Synchronous return matching API
        return "Hello, " + name;
    }
}




