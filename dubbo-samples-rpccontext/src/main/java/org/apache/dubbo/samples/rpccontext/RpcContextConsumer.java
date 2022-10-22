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

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;

import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class RpcContextConsumer {

    public static void main(String[] args) {
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ReferenceConfig<RpcContextService1> ref = new ReferenceConfig<>();
        ref.setInterface(RpcContextService1.class);
        ref.setProtocol(RpcContextUtils.dubbo_protocol);
        ref.setTimeout(30000000);
        bootstrap.application(new ApplicationConfig("rpccontext-consumer"))
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .reference(ref)
                .protocol(new ProtocolConfig(RpcContextUtils.dubbo_protocol,8000))
                .start();
        RpcContext.getClientAttachment().setAttachment(RpcContextUtils.consumer_req_key, RpcContextUtils.consumer_req_key);
        RpcContextService1 greeter = ref.get();
        greeter.sayHello();
        String provider1Res = RpcContext.getClientResponseContext().getAttachment(RpcContextUtils.provider1_res_key);
        System.out.println("get response from provider1:" + provider1Res);
        String provider2Res = (String) RpcContext.getClientResponseContext().getObjectAttachment(RpcContextUtils.provider2_res_key);
        System.out.println("get response from provider2:" + provider2Res);

    }
}
