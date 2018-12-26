package org.apache.dubbo.samples.resilience4j.impl;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.samples.resilience4j.api.RateLimiterService;

/**
 * 2018/12/26
 */
@Service
public class RateLimiterServiceImpl implements RateLimiterService {
    @Override
    public String say(String name, String value) {
        return "Hello " + name + ", this is rateLimiter. The value is :" + value;
    }
}
