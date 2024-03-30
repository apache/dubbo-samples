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

package org.apache.dubbo.samples.rpccontext;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;
import org.apache.dubbo.samples.rpccontext.dto.Service1DTO;
import org.apache.dubbo.samples.rpccontext.dto.Service2DTO;
import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RpcContextConsumer.class)
public class RpcContextServiceIT {

    @DubboReference(interfaceClass = RpcContextService1.class)
    private RpcContextService1 rpcContextService1;

    /**
     * A -> B -> C
     * Both B and C can receive the client attachment from A
     * consumer_req_key means the A request data
     * provider1_req_key 、provider1_res_key respectively means the B request data and response data
     * provider2_res_key means the C response data
     */

    @Test
    public void testRpcContext1() {
        RpcContext.getClientAttachment().setAttachment(RpcContextUtils.consumer_req_key, RpcContextUtils.consumer_req_key);
        Service1DTO service1DTO = rpcContextService1.sayHello();
        Service2DTO service2DTO = service1DTO.getService2DTO();
        String provider1Res = RpcContext.getClientResponseContext().getAttachment(RpcContextUtils.provider1_res_key);
        Assert.assertEquals(RpcContextUtils.provider1_res_key, provider1Res);

        Assert.assertEquals(RpcContextUtils.consumer_req_key, service1DTO.getConsumerReq());
        Assert.assertNull(service2DTO.getConsumerReq());

        Assert.assertEquals(RpcContextUtils.provider1_req_key, service2DTO.getProvider1Req());

        String provider2Res = (String) RpcContext.getClientResponseContext().getObjectAttachment(RpcContextUtils.provider2_res_key);
        Assert.assertNull(provider2Res);
        Assert.assertEquals(RpcContextUtils.provider2_res_key, service1DTO.getProvider2Res());
    }

}
