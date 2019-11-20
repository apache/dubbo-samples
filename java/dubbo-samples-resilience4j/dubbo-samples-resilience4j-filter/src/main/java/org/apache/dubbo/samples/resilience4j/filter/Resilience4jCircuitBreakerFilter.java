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

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.utils.CircuitBreakerUtils;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

public class Resilience4jCircuitBreakerFilter implements Filter {

    static CircuitBreaker circuitBreaker;
    static AtomicLong count = new AtomicLong(0);
    static AtomicLong breakCount = new AtomicLong(0);

    static {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(20)
                .waitDurationInOpenState(Duration.ofMillis(6000))
                .ringBufferSizeInHalfOpenState(10)
                .ringBufferSizeInClosedState(10)
                .build();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        circuitBreaker = registry.circuitBreaker("myCircuitBreaker");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("**************** Enter CircuitBreaker ****************");
        long countLong = count.incrementAndGet();
        long start = 0;
        try {
            CircuitBreakerUtils.isCallPermitted(circuitBreaker);
            start = System.nanoTime();
            Result result = invoker.invoke(invocation);
            if (result.hasException()) {
                doThrowException(result.getException(), start);
                return result;
            }
            long durationInNanos = System.nanoTime() - start;
            circuitBreaker.onSuccess(durationInNanos);
            return result;
        } catch (CircuitBreakerOpenException cbo) {

            doCircuitBreakerOpenException(cbo, countLong, breakCount.incrementAndGet());
            throw cbo;
        } catch (Throwable throwable) {
            doThrowException(throwable, start);
            throw throwable;
        }
    }

    private void doThrowException(Throwable throwable, long start) {
        long durationInNanos = System.nanoTime() - start;
        circuitBreaker.onError(durationInNanos, throwable);
    }

    private void doCircuitBreakerOpenException(Throwable throwable, long count, long breakCount) {
        System.err.println("---------------------------- Open CircuitBreaker! Try it later! ----------------------------" + breakCount + " / " + count);
    }
}
