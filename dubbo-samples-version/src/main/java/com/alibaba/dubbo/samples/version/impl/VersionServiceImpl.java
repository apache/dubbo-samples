package com.alibaba.dubbo.samples.version.impl;

import com.alibaba.dubbo.samples.version.api.VersionService;

public class VersionServiceImpl implements VersionService {

    public String sayHello(String name) {
        return "hello, " + name;
    }

}
