package com.alibaba.dubbo.samples.cache.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.dubbo.samples.cache.api.CacheService;

/**
 * ValidationServiceImpl
 */
public class CacheServiceImpl implements CacheService {

    private final AtomicInteger i = new AtomicInteger();

    public String findCache(String id) {
        return "request: " + id + ", response: " + i.getAndIncrement();
    }

}
