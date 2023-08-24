/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.samples.client;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.BaseFilter;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import java.util.concurrent.atomic.AtomicReference;

@Activate(group = "consumer")
public class ConsumerFilter implements Filter, BaseFilter.Listener {
    private static final AtomicReference<String> remoteApp = new AtomicReference<>();
    private static final AtomicReference<String> remoteAddr = new AtomicReference<>();
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        remoteApp.set(RpcContext.getContext().getRemoteApplicationName());
        remoteAddr.set(RpcContext.getContext().getRemoteAddressString());

        if (!RpcContext.getServiceContext().getRemoteApplicationName().equals(
                RpcContext.getContext().getRemoteApplicationName())) {
            throw new IllegalStateException("not equals");
        }

        if (!com.alibaba.dubbo.rpc.RpcContext.getContext().getRemoteAddressString().equals(
                RpcContext.getContext().getRemoteAddressString())) {
            throw new IllegalStateException("not equals");
        }

        if (!RpcContext.getServerContext().getRemoteAddressString().equals(
                RpcContext.getContext().getRemoteAddressString())) {
            throw new IllegalStateException("not equals");
        }
    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

    }

    public static String getRemoteApp() {
        return remoteApp.get();
    }

    public static String getRemoteAddr() {
        return remoteAddr.get();
    }
}
