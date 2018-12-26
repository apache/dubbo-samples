package org.apache.dubbo.samples.resilience4j.filter;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

/**
 * @author cvictory ON 2018/12/25
 */
public class Resilience4jRateLimiterFilter implements Filter {

    static CircuitBreaker circuitBreaker;

    static {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(20)
                .ringBufferSizeInClosedState(5)
                .build();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        circuitBreaker = registry.circuitBreaker("my");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long start = System.nanoTime();
        try {
            Result result = invoker.invoke(invocation);
            long durationInNanos = System.nanoTime() - start;
            circuitBreaker.onSuccess(durationInNanos);
            return result;
        } catch (Throwable throwable) {
            long durationInNanos = System.nanoTime() - start;
            circuitBreaker.onError(durationInNanos, throwable);
            throw throwable;
        }
    }
}
