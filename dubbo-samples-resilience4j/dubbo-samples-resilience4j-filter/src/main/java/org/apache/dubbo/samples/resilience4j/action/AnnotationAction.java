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

import org.springframework.stereotype.Component;

@Component("annotationAction")
public class AnnotationAction {

    @Reference(interfaceClass = AnnotationService.class)
    private AnnotationService annotationService;

    @Reference(interfaceClass = CircuitBreakerService.class)
    private CircuitBreakerService circuitBreakerService;

    @Reference(interfaceClass = RateLimiterService.class)
    private RateLimiterService rateLimiterService;

    public String doSayHello(String name) {
        return annotationService.sayHello(name);
    }

    public void sayCircuitBreaker(String name) {
        new Thread(() -> {
            {
                int i = 0;
                while (true) {
                    doSayCircuitBreaker("off", name, 20, ++i);
                    doSayCircuitBreaker("on", name, 25, ++i);
                    doSayCircuitBreaker("half", name, 30, ++i);
                    doSayCircuitBreaker("off", name, 30, ++i);
                    doSayCircuitBreaker("half", name, 30, ++i);
                    doSayCircuitBreaker("off", name, 15, ++i);
                }
            }
        }).start();

    }

    private void doSayCircuitBreaker(String tag, String name, int c, int l) {
        System.out.println("--------------------- start to run circuitBreak: " + tag + name + "---------------------");
        for (int i = 0; i < c; i++) {
            try {
                Thread.sleep(20);
                System.out.print(System.currentTimeMillis() + " - ");
                System.out.println(circuitBreakerService.say(tag + "-" + name + "=" + l + ":" + i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Throwable t) {
                System.err.println("==" + t.getMessage());
            }
        }
    }

    public void sayRateLimiter(String name, String value) {
        new Thread(() -> {
            {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(40);
                        System.out.println(rateLimiterService.say(name + (i++), value + i));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<< " + e.getMessage() + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    }
                }
            }
        }).start();
    }

    public String reliable(String name) {
        return "hystrix fallback value";
    }

}
