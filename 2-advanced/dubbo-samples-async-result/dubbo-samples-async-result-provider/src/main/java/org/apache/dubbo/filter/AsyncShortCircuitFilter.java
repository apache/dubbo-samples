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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Activate(group = {CommonConstants.PROVIDER}, order = -2000)
public class AsyncShortCircuitFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AsyncShortCircuitFilter.class);
    private final Executor executor = Executors.newCachedThreadPool();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String method = invocation.getMethodName();
        // if (someCondition) {
        //     return invoker.invoke(invocation);  // Pass through when condition not met
        // }

        logger.debug(" method={}, not calling actual service", method);

        CompletableFuture<AppResponse> future = new CompletableFuture<>();

        executor.execute(() -> {
            try {
                String payload = "[short-circuit] async result for " + method;
                AppResponse resp = new AppResponse(invocation);
                resp.setValue(payload);
                future.complete(resp);
                logger.debug(payload);
            } catch (Throwable t) {
                AppResponse resp = new AppResponse(invocation);
                resp.setException(t);
                future.complete(resp);
                logger.debug(t.getMessage());
            }
        });

        return new AsyncRpcResult(future, invocation);
    }
}
