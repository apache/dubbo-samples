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

import org.apache.dubbo.config.*;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService2;
import org.apache.dubbo.samples.rpccontext.dto.Service1DTO;
import org.apache.dubbo.samples.rpccontext.dto.Service2DTO;
import org.apache.dubbo.samples.rpccontext.impl.RpcContextImpl1;
import org.apache.dubbo.samples.rpccontext.utils.RpcContextUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/dubbo-rpccontext-consumer.xml"})
public class RpcContextServiceIT {

    @Autowired
    private RpcContextService1 service;

    /**
     * A -> B -> C
     * Both B and C can receive the client attachment from A
     */

    @Test
    public void testAttachment1() {
        RpcContext.getClientAttachment().setAttachment(RpcContextUtils.consumer_req_key, RpcContextUtils.consumer_req_key);
        Service1DTO service1DTO = service.sayHello();
        Service2DTO service2DTO = service1DTO.getService2DTO();
        String provider1Res = RpcContext.getClientResponseContext().getAttachment(RpcContextUtils.provider1_res_key);
        Assert.assertEquals(RpcContextUtils.provider1_res_key, provider1Res);

        Assert.assertEquals(RpcContextUtils.consumer_req_key, service1DTO.getConsumerReq());
        Assert.assertEquals(null, service2DTO.getConsumerReq());

        Assert.assertEquals(RpcContextUtils.provider1_req_key, service2DTO.getProvider1Req());

        String provider2Res = (String) RpcContext.getClientResponseContext().getObjectAttachment(RpcContextUtils.provider2_res_key);
        Assert.assertEquals(null, provider2Res);
        Assert.assertEquals(RpcContextUtils.provider2_res_key, service1DTO.getProvider2Res());
    }

    /**
     * A -> B -> C
     * Only C can receive the client attachment from A
     */
    @Test
    public void testAttachment2() {
        RpcContext.getClientAttachment().setObjectAttachment(RpcContextUtils.provider1_req_key, RpcContextUtils.provider1_req_key);
//        String result = service.sayHello();
//        Assert.assertTrue(result.startsWith("Hello dubbo"));
//        Assert.assertTrue(result.endsWith("index: 1"));
//        RpcContext.getContext().setAttachment("index", "2");
//        result = service.sayHello("dubbo");
//        Assert.assertTrue(result.endsWith("index: 2"));
//        result = service.sayHello("dubbo");
//        Assert.assertTrue(result.endsWith("index: null"));
    }

    /**
     * A -> B -> C
     * Both A and B can receive the response context from C
     */
    @Test
    public void testResponseContext1() {
        RpcContext.getClientAttachment().setObjectAttachment(RpcContextUtils.provider1_req_key, RpcContextUtils.provider1_req_key);
//        String result = service.sayHello();
//        Assert.assertTrue(result.startsWith("Hello dubbo"));
//        Assert.assertTrue(result.endsWith("index: 1"));
//        RpcContext.getContext().setAttachment("index", "2");
//        result = service.sayHello("dubbo");
//        Assert.assertTrue(result.endsWith("index: 2"));
//        result = service.sayHello("dubbo");
//        Assert.assertTrue(result.endsWith("index: null"));
    }

    /**
     * A -> B -> C
     * Only A can receive the response context from C
     */
    @Test
    public void testResponseContext2() {
        RpcContext.getClientAttachment().setObjectAttachment(RpcContextUtils.provider1_req_key, RpcContextUtils.provider1_req_key);
//        String result = service.sayHello();
//        Assert.assertTrue(result.startsWith("Hello dubbo"));
//        Assert.assertTrue(result.endsWith("index: 1"));
//        RpcContext.getContext().setAttachment("index", "2");
//        result = service.sayHello("dubbo");
//        Assert.assertTrue(result.endsWith("index: 2"));
//        result = service.sayHello("dubbo");
//        Assert.assertTrue(result.endsWith("index: null"));
    }


}
