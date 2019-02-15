/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.samples.resilience4jboot2.consumer.controller;

import org.apache.dubbo.samples.resilience4jboot2.consumer.action.AnnotationAction;
import org.apache.dubbo.samples.resilience4jboot2.consumer.action.CircuitBreakerAction;
import org.apache.dubbo.samples.resilience4jboot2.consumer.action.RateLimiterAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2018/12/28
 */
@RestController
public class TestController {

    @Autowired
    private AnnotationAction annotationAction;

    @Autowired
    private CircuitBreakerAction circuitBreakerAction;

    @Autowired
    private RateLimiterAction rateLimiterAction;

    @RequestMapping("/test")
    public String test() {
        return annotationAction.doSayHello(" test");
    }

    @RequestMapping("/rate")
    public String rate() {
        rateLimiterAction.sayRateLimiter();
        return "look at console . rate";
    }

    @RequestMapping("/suspend_rate")
    public String suspend_rate() {
        rateLimiterAction.suspendRateLimiter();
        return "look at console . suspend_rate";
    }

    @RequestMapping("/resume_rate")
    public String resume_rate() {
        rateLimiterAction.resumeRateLimiterr();
        return "look at console . resume_rate";
    }

    @RequestMapping("/circuitBreaker")
    public String circuitBreaker() {
        circuitBreakerAction.sayCircuitBreaker();
        return "look at console. circuitBreaker";
    }

    @RequestMapping("/suspend_circuit_breaker")
    public String suspend_CircuitBreaker() {
        circuitBreakerAction.suspendCircuitBreaker();
        return "look at console . suspend_CircuitBreaker";
    }

    @RequestMapping("/resume_circuit_breaker")
    public String resume_CircuitBreaker() {
        circuitBreakerAction.resumeCircuitBreaker();
        return "look at console . resume_CircuitBreaker";
    }

    @RequestMapping("/circuitBreakerMethod")
    public String circuitBreakerMethod() {
        circuitBreakerAction.sayCircuitBreakerMethod();
        return "look at console. circuitBreakerMethod";
    }

    @RequestMapping("/suspend_circuit_breaker_method")
    public String suspendCircuitBreakerMethod() {
        circuitBreakerAction.suspendCircuitBreakerMethod();
        return "look at console . suspend_CircuitBreakerMethod";
    }

    @RequestMapping("/resume_circuit_breaker_method")
    public String resumeCircuitBreakerMethod() {
        circuitBreakerAction.resumeCircuitBreakerMethod();
        return "look at console . resume_CircuitBreakerMethod";
    }

}
