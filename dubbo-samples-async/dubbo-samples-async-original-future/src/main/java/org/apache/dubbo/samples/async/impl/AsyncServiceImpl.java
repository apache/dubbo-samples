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
import org.apache.dubbo.rpc.RpcContextAttachment;
import org.apache.dubbo.samples.async.api.AsyncService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class AsyncServiceImpl implements AsyncService {
    private static Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Override
    public CompletableFuture<String> sayHello(String name) {
        // If attachments and context are going to be used in the new thread, startAsync() and signalContextSwitch() must be called.
        // Otherwise, it is not necessary to call these two methods.
        AsyncContext asyncContext = RpcContext.startAsync();
        return CompletableFuture.supplyAsync(() -> {
            asyncContext.signalContextSwitch();
            RpcContextAttachment attachmentFromClient = RpcContext.getServerAttachment();
            RpcContextAttachment attachmentToClient = RpcContext.getServerContext();
            String received = (String) attachmentFromClient.getAttachment("consumer-key1");
            logger.info("consumer-key1 from attachment: " + received);
            attachmentToClient.setAttachment("server-key1", "server-" + received);

            received = (String) attachmentFromClient.getAttachment("filters");
            logger.info("filters from attachment: " + received);
            attachmentToClient.setAttachment("filters", received);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "async response from provider.";
        });
    }

}
