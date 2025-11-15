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

import java.util.concurrent.CompletableFuture;

@Activate(group = {CommonConstants.PROVIDER}, order = -1000)
public class DecorateResultFilter implements Filter {

    @Override
    public AsyncRpcResult invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        Result result = invoker.invoke(invocation);

        if (result instanceof AsyncRpcResult) {
            AsyncRpcResult ar = (AsyncRpcResult) result;
            CompletableFuture<AppResponse> f = ar.getResponseFuture();

            CompletableFuture<AppResponse> decorated = f.thenApply(app -> {
                if (!app.hasException()) {
                    Object v = app.getValue();
                    AppResponse newResp = new AppResponse(invocation);
                    newResp.setValue("[decorated] " + v);
                    return newResp;
                }
                return app;
            });

            return new AsyncRpcResult(decorated, invocation);
        } else {
            String decorated = "[decorated] " + result.getValue();
            return AsyncRpcResult.newDefaultAsyncResult(decorated, invocation);
        }
    }
}
