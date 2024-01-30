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

package org.apache.dubbo.samples.cat.provider;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.cat.api.DemoService;
import org.apache.dubbo.samples.cat.api.context.CatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DubboService
public class DemoServiceImpl implements DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    private static final String NAME = "cat-provider";

    @Override
    public String sayHello(String name, CatContext catContext) {
        logger.info("Hello {}, request from consumer: {}", name, RpcContext.getContext().getRemoteAddress());

        // Client Tracking
        Transaction t = Cat.newTransaction("Service", NAME);
        try {
            Cat.logEvent("Service.client", NAME);
            Cat.logEvent("Service.app", NAME);
            Cat.logRemoteCallClient(catContext);
        } catch (Exception e) {
            Cat.logError(e);
            t.setStatus(e);
        } finally {
            t.setStatus(Transaction.SUCCESS);
            t.complete();
        }
        return "hello " + name;
    }

}
