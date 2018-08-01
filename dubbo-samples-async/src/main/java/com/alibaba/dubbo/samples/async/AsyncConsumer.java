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

package com.alibaba.dubbo.samples.async;

import com.alibaba.dubbo.samples.async.api.AsyncService;

import org.apache.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * CallbackConsumer
 */
public class AsyncConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/async-consumer.xml"});
        context.start();

        final AsyncService service = (AsyncService) context.getBean("asyncService");

        CompletableFuture<String> f = RpcContext.getContext().asyncCall(() -> service.sayHello("async call request"));


        System.out.println("async call ret :" + f.get());

        RpcContext.getContext().asyncCall(new Runnable() {
            public void run() {
                service.sayHello("oneway call request1");
                service.sayHello("oneway call request2");
            }
        });

        service.goodbye("samples");
        Future<String> future = RpcContext.getContext().getFuture();
        String result = future.get();
        System.out.println(result);

        System.in.read();
    }
}
