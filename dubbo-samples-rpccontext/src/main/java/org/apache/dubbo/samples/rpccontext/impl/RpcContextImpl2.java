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

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService2;
import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RpcContextImpl2 implements RpcContextService2 {

    @Override
    public void sayHi(){
        String consumerReq = RpcContext.getServerAttachment().getAttachment(RpcContextUtils.consumer_req_key);
        System.out.println("get request from consumer："+ consumerReq);
        String provider1Req =  (String) RpcContext.getServerAttachment().getObjectAttachment(RpcContextUtils.provider1_req_key);
        System.out.println("get request from provider1："+ provider1Req);
        RpcContext.getServerResponseContext().setAttachment(RpcContextUtils.provider2_res_key, RpcContextUtils.provider2_res_key);
    }
}
