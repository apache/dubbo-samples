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

package org.apache.dubbo.samples.resilience4j.action;

import org.apache.dubbo.config.annotation.Reference;


import org.apache.dubbo.samples.resilience4j.api.AnnotationService;
import org.apache.dubbo.samples.resilience4j.api.CircuitBreakerService;
import org.apache.dubbo.samples.resilience4j.api.RateLimiterService;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.stereotype.Component;

/**
 * AnnotationAction
 */
@Component("annotationAction")
public class AnnotationAction {

    @Reference
    private AnnotationService annotationService;
    @Reference
    private CircuitBreakerService circuitBreakerService;
    @Reference
    private RateLimiterService rateLimiterService;

    public String doSayHello(String name) {
        return annotationService.sayHello(name);
    }

    public void sayCircuitBreaker(String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    int i = 0;
                    while (true) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(circuitBreakerService.say(name + (i++)));
                    }
                }
            }
        }).start();

    }

    public void sayRateLimiter(String name, String value) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    int i = 0;
                    while (true) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(rateLimiterService.say(name + (i++), value + i));
                    }
                }
            }
        }).start();
    }

    public String reliable(String name) {
        return "hystrix fallback value";
    }

}
