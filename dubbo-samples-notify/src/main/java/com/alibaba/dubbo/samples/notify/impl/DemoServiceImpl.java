package com.alibaba.dubbo.samples.notify.impl;

import com.alibaba.dubbo.samples.notify.api.DemoService;

/**
 * @author zmx ON 2018/4/26
 */
public class DemoServiceImpl implements DemoService{

    public String sayHello(int id) {
        return "aaa";
    }
}
