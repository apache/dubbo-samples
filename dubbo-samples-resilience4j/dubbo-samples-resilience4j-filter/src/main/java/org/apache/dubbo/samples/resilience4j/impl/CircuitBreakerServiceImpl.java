package org.apache.dubbo.samples.resilience4j.impl;

import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.samples.resilience4j.api.CircuitBreakerService;

/**
 * @author cvictory ON 2018/12/26
 */
@Service
public class CircuitBreakerServiceImpl implements CircuitBreakerService {
    @Override
    public String say(String name) {
        throw new RuntimeException("Exception to show resilience enabled.");
    }
}
