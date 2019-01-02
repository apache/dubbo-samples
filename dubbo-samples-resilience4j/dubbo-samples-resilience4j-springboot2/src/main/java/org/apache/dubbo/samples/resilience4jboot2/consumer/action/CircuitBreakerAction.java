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

package org.apache.dubbo.samples.resilience4jboot2.consumer.action;

import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AnnotationAction
 */
@Component("circuitBreakerAction")
public class CircuitBreakerAction {

    @Autowired
    private CircuitBreakTypeWrapper circuitBreakTypeWrapper;

    @Autowired
    private CircuitBreakMethodWrapper circuitBreakMethodWrapper;


    private Thread circuitBreakerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            {
                int i = 0;
                while (true) {
                    doSayCircuitBreaker("off", "circuitBreaker", 20, ++i);
                    doSayCircuitBreaker("on", "circuitBreaker", 25, ++i);
                    doSayCircuitBreaker("half", "circuitBreaker", 30, ++i);
                    doSayCircuitBreaker("off", "circuitBreaker", 30, ++i);
                    doSayCircuitBreaker("half", "circuitBreaker", 30, ++i);
                    doSayCircuitBreaker("off", "circuitBreaker", 15, ++i);
                }
            }
        }
    });

    private Thread circuitBreakerMethodThread = new Thread(new Runnable() {
        @Override
        public void run() {
            {
                int i = 0;
                while (true) {
                    doSayCircuitBreakerMethod("off", "circuitBreaker", 20, ++i);
                    doSayCircuitBreakerMethod("on", "circuitBreaker", 8, ++i);
                    doSayCircuitBreakerMethod("half", "circuitBreaker", 30, ++i);
                    doSayCircuitBreakerMethod("off", "circuitBreaker", 30, ++i);
                    doSayCircuitBreakerMethod("half", "circuitBreaker", 30, ++i);
                    doSayCircuitBreakerMethod("off", "circuitBreaker", 32, ++i);
                }
            }
        }
    });

    public void sayCircuitBreaker() {
        if (circuitBreakerThread.getState() == Thread.State.NEW) {
            circuitBreakerThread.start();
        }
    }

    public void suspendCircuitBreaker() {
        circuitBreakerThread.suspend();
    }

    public void resumeCircuitBreaker() {
        circuitBreakerThread.resume();

    }

    private void doSayCircuitBreaker(String tag, String name, int c, int l) {
        System.out.println("--------------------- start to run circuitBreak: " + tag + name + "---------------------");
        for (int i = 0; i < c; i++) {
            try {
                Thread.sleep(20);
                System.out.print(System.currentTimeMillis() + " - ");
                System.out.println(circuitBreakTypeWrapper.say(tag + "-" + name + "=" + l + ":" + i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (CircuitBreakerOpenException t) {
                System.err.println("== " + l + ":" + i + " " + t.toString());
            } catch (Throwable t) {
                System.err.println("== " + l + ":" + i + " " + t.toString());
            }
        }
    }


    public void sayCircuitBreakerMethod() {
        if (circuitBreakerMethodThread.getState() == Thread.State.NEW) {
            circuitBreakerMethodThread.start();
        }
    }

    public void suspendCircuitBreakerMethod() {
        circuitBreakerMethodThread.suspend();
    }

    public void resumeCircuitBreakerMethod() {
        circuitBreakerMethodThread.resume();

    }

    private void doSayCircuitBreakerMethod(String tag, String name, int c, int l) {
        System.out.println("--------------------- start to run circuitBreak: " + tag + name + "---------------------");
        for (int i = 0; i < c; i++) {
            try {
                Thread.sleep(20);
                System.out.print(System.currentTimeMillis() + " - ");
                System.out.println(circuitBreakMethodWrapper.say(tag + "-" + name + "=" + l + ":" + i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (CircuitBreakerOpenException t) {
                System.err.println("== " + l + ":" + i + " " + t.toString());
            } catch (Throwable t) {
                System.err.println("== " + l + ":" + i + " " + t.toString());
            }
        }
    }

}
