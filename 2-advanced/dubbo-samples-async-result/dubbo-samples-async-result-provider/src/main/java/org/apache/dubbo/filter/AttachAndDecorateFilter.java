/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Activate(group = {CommonConstants.PROVIDER}, order = -900)
public class AttachAndDecorateFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AttachAndDecorateFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation inv) throws RpcException {
        Result r = invoker.invoke(inv);

        if (r instanceof AsyncRpcResult) {
            AsyncRpcResult ar = (AsyncRpcResult) r;

            CompletableFuture<AppResponse> decorated = ar.getResponseFuture().thenApply(app -> {

                String traceId = UUID.randomUUID().toString().substring(0, 8);
                app.setAttachment("trace-id", traceId);
                app.setAttachment("filter", "AttachAndDecorateFilter");

                if (app.hasException()) {
                    return app;
                }

                if (app.getValue() instanceof String s) {
                    app.setValue("[attach&decorated] " + s);
                }

                return app;
            });

            return new AsyncRpcResult(decorated, inv);
        } else {

            AppResponse app = new AppResponse(inv);

            String traceId = UUID.randomUUID().toString().substring(0, 8);
            app.setAttachment("trace-id", traceId);
            app.setAttachment("filter", "AttachAndDecorateFilter");

            Object originalValue = r.getValue();
            if (originalValue instanceof String) {
                app.setValue("[attach&decorated] " + originalValue);
            } else {
                app.setValue(originalValue);
            }
            return AsyncRpcResult.newDefaultAsyncResult(app, inv);
        }
    }
}
