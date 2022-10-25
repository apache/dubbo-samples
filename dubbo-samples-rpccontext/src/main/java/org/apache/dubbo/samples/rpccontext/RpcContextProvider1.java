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

package org.apache.dubbo.samples.rpccontext;

import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;
import org.apache.dubbo.samples.rpccontext.impl.RpcContextImpl1;
import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;


public class RpcContextProvider1 {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-rpccontext-provider1.xml");
        context.start();
        System.out.println("Rpc context provider1 started");
        new CountDownLatch(1).await();
    }
//    public static void main(String[] args) throws IOException {
//        ServiceConfig<RpcContextService1> service = new ServiceConfig<>();
//        service.setInterface(RpcContextService1.class);
//        service.setRef(new RpcContextImpl1());
//        ReferenceConfig<RpcContextService1> ref = new ReferenceConfig<>();
//        ref.setInterface(RpcContextService1.class);
//        ref.setProtocol(RpcContextUtils.dubbo_protocol);
//        ref.setTimeout(30000000);
//        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
//        bootstrap.application(new ApplicationConfig("rpccontext-provider-1"))
//                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
//                .protocol(new ProtocolConfig(RpcContextUtils.dubbo_protocol, 8000))
//                .service(service)
//                .reference(ref)
//                .start()
//                .await()
//        ;
//        System.out.println("Rpc context provider1 started");
//    }
}
