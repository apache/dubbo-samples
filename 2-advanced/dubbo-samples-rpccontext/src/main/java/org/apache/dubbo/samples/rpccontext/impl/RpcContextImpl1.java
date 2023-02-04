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

package org.apache.dubbo.samples.rpccontext.impl;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService2;
import org.apache.dubbo.samples.rpccontext.dto.Service1DTO;
import org.apache.dubbo.samples.rpccontext.dto.Service2DTO;
import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component("demoService")
public class RpcContextImpl1 implements RpcContextService1 {

    @Override
    public Service1DTO sayHello(){
        Service1DTO service1DTO = new Service1DTO();
        String consumerReq = RpcContext.getServerAttachment().getAttachment(RpcContextUtils.consumer_req_key);
        System.out.println("get request from consumer："+ consumerReq);
        ReferenceConfig<RpcContextService2> ref = new ReferenceConfig<>();
        ref.setInterface(RpcContextService2.class);
        ref.setProtocol(RpcContextUtils.dubbo_protocol);
        ref.setTimeout(30000000);
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        RpcContextService2 greeterSayHi = bootstrap.getCache().get(ref);
        RpcContext.getClientAttachment().setObjectAttachment(RpcContextUtils.provider1_req_key, RpcContextUtils.provider1_req_key);
        Service2DTO service2DTO = greeterSayHi.sayHi();
        String provider2Res = (String)RpcContext.getClientResponseContext().getObjectAttachment(RpcContextUtils.provider2_res_key);
        System.out.println("get response from provider2："+ provider2Res);
        RpcContext.getServerResponseContext().setObjectAttachment(RpcContextUtils.provider1_res_key, RpcContextUtils.provider1_res_key);
        service1DTO.setConsumerReq(consumerReq);
        service1DTO.setProvider2Res(provider2Res);
        service1DTO.setService2DTO(service2DTO);
        return service1DTO;
    }
}