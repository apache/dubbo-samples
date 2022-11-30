/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.samples.async.impl;

import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.async.api.AsyncService;
import org.apache.dubbo.samples.async.api.EmbeddedAsyncService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncServiceImpl implements AsyncService {
    private static Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    private EmbeddedAsyncService embeddedService;

    @Override
    public String sayHello(String name) {
        AsyncContext asyncContext = RpcContext.startAsync();
        logger.info("sayHello start");

        new Thread(() -> {
            asyncContext.signalContextSwitch();
            logger.info("Attachment from consumer: " + RpcContext.getContext().getAttachment("consumer-key1"));
            logger.info("async start");
            String embeddedCallResult = null;
            if (!name.endsWith("IT")) {
                embeddedCallResult = embeddedService.sayHello("embedded call");
            }
            try {
                Thread.sleep(5000);
                logger.info("  embedded call result is " + embeddedCallResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            asyncContext.write("Hello " + name + ", "
                    + (embeddedCallResult == null ? "" : embeddedCallResult + " ")
                    + "response from provider.");
            logger.info("async end");
        }).start();

        logger.info("sayHello end");
        return "hello, " + name;
    }

    public EmbeddedAsyncService getEmbeddedService() {
        return embeddedService;
    }

    public void setEmbeddedService(EmbeddedAsyncService embeddedService) {
        this.embeddedService = embeddedService;
    }
}
