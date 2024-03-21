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

import java.util.logging.Logger;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService2;
import org.apache.dubbo.samples.rpccontext.dto.Service2DTO;
import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;

@DubboService
public class RpcContextImpl2 implements RpcContextService2 {
    private static final Logger LOGGER = Logger.getLogger(RpcContextImpl2.class.getName());

    @Override
    public Service2DTO sayHi() {
        Service2DTO service2DTO = new Service2DTO();
        String consumerReq = RpcContext.getServerAttachment().getAttachment(RpcContextUtils.consumer_req_key);
        LOGGER.info("get request from consumer：" + consumerReq);
        String provider1Req = (String) RpcContext.getServerAttachment().getObjectAttachment(RpcContextUtils.provider1_req_key);
        LOGGER.info("get request from provider1：" + provider1Req);
        RpcContext.getServerResponseContext().setAttachment(RpcContextUtils.provider2_res_key, RpcContextUtils.provider2_res_key);
        service2DTO.setConsumerReq(consumerReq);
        service2DTO.setProvider1Req(provider1Req);
        return service2DTO;
    }

}
