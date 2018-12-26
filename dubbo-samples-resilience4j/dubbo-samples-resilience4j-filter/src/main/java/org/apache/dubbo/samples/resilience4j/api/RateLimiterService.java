package org.apache.dubbo.samples.resilience4j.api;

/**
 * 2018/12/26
 */
public interface RateLimiterService {

    String say(String name, String value);
}
