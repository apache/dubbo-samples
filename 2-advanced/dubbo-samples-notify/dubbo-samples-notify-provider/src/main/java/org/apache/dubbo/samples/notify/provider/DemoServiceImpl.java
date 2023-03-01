package org.apache.dubbo.samples.notify.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.notify.DemoService;


@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(int id) {
        if (id > 10) {
            throw new RuntimeException("exception from sayHello: too large id");
        }
        return "demo" + id;
    }
}
