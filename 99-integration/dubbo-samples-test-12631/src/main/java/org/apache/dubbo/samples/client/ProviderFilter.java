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
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

@Activate(group = "provider")
public class ProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (!"test-consumer".equals(RpcContext.getContext().getRemoteApplicationName())) {
            throw new IllegalArgumentException("Remote application name is not test-consumer. " +
                    "Actual: " + RpcContext.getContext().getRemoteApplicationName());
        }

        if (StringUtils.isEmpty(RpcContext.getContext().getRemoteAddressString())) {
            throw new IllegalArgumentException("Remote address is empty. " +
                    "Actual: " + RpcContext.getContext().getRemoteAddressString());
        }

        if (StringUtils.isEmpty(com.alibaba.dubbo.rpc.RpcContext.getContext().getRemoteAddressString())) {
            throw new IllegalArgumentException("Remote address is empty. " +
                    "Actual: " + RpcContext.getContext().getRemoteAddressString());
        }

        if (!"test-consumer".equals(RpcContext.getServiceContext().getRemoteApplicationName())) {
            throw new IllegalArgumentException("Remote application name is not test-consumer. " +
                    "Actual: " + RpcContext.getContext().getRemoteApplicationName());
        }

        if (StringUtils.isEmpty(RpcContext.getServiceContext().getRemoteAddressString())) {
            throw new IllegalArgumentException("Remote address is empty. " +
                    "Actual: " + RpcContext.getContext().getRemoteAddressString());
        }
        return invoker.invoke(invocation);
    }

}
