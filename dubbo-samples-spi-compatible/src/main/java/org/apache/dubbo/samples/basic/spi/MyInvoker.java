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
package org.apache.dubbo.samples.basic.spi;

import org.apache.dubbo.rpc.AppResponse;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.samples.basic.api.DemoService;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

public class MyInvoker<T> implements Invoker<T> {

    @Override
    public Class<T> getInterface() {
        return null;
    }

    @Override
    public org.apache.dubbo.rpc.Result invoke(Invocation invocation) throws org.apache.dubbo.rpc.RpcException {
        AsyncRpcResult result = new AsyncRpcResult(invocation);
        result.setValue("hello");
        return result;
    }

    @Override
    public Result invoke(com.alibaba.dubbo.rpc.Invocation invocation) throws RpcException {
        return new Result.CompatibleResult(new AppResponse());
    }

    @Override
    public URL getUrl() {
        return URL.valueOf("compatible://localhost:20880/" + DemoService.class.getName());
    }

    @Override
    public boolean isAvailable() {
        return true;
    }


    @Override
    public void destroy() {

    }
}
