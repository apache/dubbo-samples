/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.apache.dubbo.sample.basic;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;

public class ApiWrapperConsumer {
    public static void main(String[] args) {
        ReferenceConfig<IGreeter2> ref = new ReferenceConfig<>();
        ref.setInterface(IGreeter2.class);
        ref.setCheck(false);
        ref.setProtocol("tri");
        ref.setLazy(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-consumer"))
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(ref)
                .start();

        final IGreeter2 iGreeter = ref.get();
        System.out.println("dubbo ref started");
        long st = System.currentTimeMillis();
        String reply = iGreeter.sayHello0("haha");
        // 4MB response
        System.out.println("Reply len:" + reply.length() + " cost:" + (System.currentTimeMillis() - st));

        try {
            final String exception = iGreeter.sayHelloException("exception");
        } catch (Throwable t) {
            System.out.println("Exception:" + t.getMessage());
        }

        RpcContext.getClientAttachment().setAttachment("str", "str");
        final String attachment = iGreeter.sayHelloWithAttachment("attachment");
        System.out.println(RpcContext.getServerContext().getObjectAttachments());

    }
}
