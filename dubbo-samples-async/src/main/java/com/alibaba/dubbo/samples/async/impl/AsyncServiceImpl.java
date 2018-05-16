package com.alibaba.dubbo.samples.async.impl;

import com.alibaba.dubbo.samples.async.api.AsyncService;

/**
 * AsyncServiceImpl
 */
public class AsyncServiceImpl implements AsyncService {

    public String sayHello(String name) {
        System.out.println("async provider received: " + name);
        return "hello, " + name;
    }

}
