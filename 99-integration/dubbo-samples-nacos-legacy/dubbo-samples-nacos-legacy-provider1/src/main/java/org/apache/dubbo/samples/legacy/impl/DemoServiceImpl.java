package org.apache.dubbo.samples.legacy.impl;

import org.apache.dubbo.samples.legacy.api.DemoService;

import com.alibaba.dubbo.common.Version;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello() {
        return "hello from " + Version.getVersion();
    }
}
