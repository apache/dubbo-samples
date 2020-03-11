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
package org.apache.dubbo.samples.async.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import com.alibaba.dubbo.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class LegacyBlockFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(LegacyBlockFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String filters = (String) context.getAttachment("filters");
        if (StringUtils.isEmpty(filters)) {
            filters = "";
        }
        filters += " legacy-block-filter";
        context.setAttachment("filters", filters);

        Result result = invoker.invoke(invocation);

        logger.info("This is the default return value: " + result.getValue());

        if (result.hasException()) {
            System.out.println("LegacyBlockFilter: This will only happen when the real exception returns: " + result.getException());
            logger.warn("This will only happen when the real exception returns", result.getException());
        }

        logger.info("LegacyBlockFilter: This msg should not be blocked.");
        return result;
    }
}
