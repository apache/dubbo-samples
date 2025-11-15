package org.apache.dubbo.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.AppResponse;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Activate(group = {CommonConstants.PROVIDER}, order = -800)
public class EventDrivenFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(EventDrivenFilter.class);

    private final MockAsyncClient client = new MockAsyncClient();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String methodName = invocation.getMethodName();

        /* ========== Pass-through mode (uncomment if needed) ==========
        // Directly call subsequent services, no event-driven processing
        logger.debug("EventDrivenFilter: Pass-through mode, method={}", methodName);
        return invoker.invoke(invocation);
        ========== Pass-through mode end ========== */

        // ========== Default: Event-driven mode ==========
        // Can selectively apply event-driven mode to certain methods
        // if (!"sayHelloAsync".equals(methodName)) {
        //     return invoker.invoke(invocation);  // Pass through
        // }

        logger.debug("EventDrivenFilter: Event-driven mode, intercepting method {}, delegating to external async client", methodName);

        CompletableFuture<AppResponse> future = new CompletableFuture<>();
        AsyncRpcResult ar = new AsyncRpcResult(future, invocation);

        client.send(invocation, (value, err) -> {
            AppResponse resp = new AppResponse(invocation);
            if (err != null) {
                resp.setException(err);
            } else {
                resp.setValue(value);
            }
            future.complete(resp);
        });

        ar.whenCompleteWithContext((r, t) -> {
            if (t != null) {
                logger.error("exceptionally, method={}", methodName, t);
            } else {
                logger.debug("normally, method={}", methodName);
            }
        });

        return ar;
    }

    private static class MockAsyncClient {
        private static final Logger logger = LoggerFactory.getLogger(MockAsyncClient.class);
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        public void send(Invocation invocation, Callback callback) {
            String methodName = invocation.getMethodName();
            Object[] args = invocation.getArguments();

            logger.debug("method={}", methodName);

            scheduler.schedule(() -> {
                try {
                    String result = String.format(
                        "[Event-driven] External service processing result: method=%s, args=%s",
                        methodName,
                        args.length > 0 ? args[0] : "none"
                    );
                    callback.onComplete(result, null);
                } catch (Exception e) {
                    callback.onComplete(null, e);
                }
            }, 500, TimeUnit.MILLISECONDS);
        }
    }

    @FunctionalInterface
    interface Callback {
        void onComplete(Object value, Throwable error);
    }
}
