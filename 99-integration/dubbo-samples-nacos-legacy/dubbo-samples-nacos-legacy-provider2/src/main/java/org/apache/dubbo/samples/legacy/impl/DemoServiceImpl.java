package org.apache.dubbo.samples.legacy.impl;

import org.apache.dubbo.common.Version;
import org.apache.dubbo.samples.legacy.api.DemoService;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello() {
        return "hello from " + Version.getVersion();
    }
}
