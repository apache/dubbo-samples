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
package org.apache.dubbo.samples.resilience4j.filter;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;

import java.time.Duration;


public class Resilience4jRateLimiterFilter implements Filter {

    private static RateLimiter rateLimiter;

    static {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(1000))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofMillis(200))
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);
        rateLimiter = rateLimiterRegistry.rateLimiter("myRateLimiter");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            System.out.println("**************** Enter RateLimiter ****************");
            RateLimiter.waitForPermission(rateLimiter);
            return invoker.invoke(invocation);
        } catch (RequestNotPermitted rnp) {
            System.err.println("---------------- Rate Limiter! Try it later! ----------------");
            throw rnp;
        } catch (Throwable throwable) {
            System.err.println("........" + throwable.getMessage());
            throw throwable;
        }
    }
}
