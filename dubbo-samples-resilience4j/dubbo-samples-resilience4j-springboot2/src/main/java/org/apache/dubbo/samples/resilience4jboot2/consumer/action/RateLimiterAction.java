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

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.samples.resilience4jboot2.api.RateLimiterService;
import org.springframework.stereotype.Component;

/**
 * AnnotationAction
 */
@Component("rateLimiterAction")
public class RateLimiterAction {

    @Reference(interfaceClass = RateLimiterService.class)
    private RateLimiterService rateLimiterService;

    private Thread rateThread = new Thread(new Runnable() {
        @Override
        public void run() {
            {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(40);
                        System.out.println(rateLimiterService.say("rateK" + (i++), "rateV" + i));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<<< " + e.getMessage() + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    }
                }
            }
        }
    });


    public void sayRateLimiter() {
        if (rateThread.getState() == Thread.State.NEW) {
            rateThread.start();
        }
    }

    public void suspendRateLimiter() {
        rateThread.suspend();
    }

    public void resumeRateLimiterr() {
        rateThread.resume();

    }


    public String reliable(String name) {
        return "hystrix fallback value";
    }

}
