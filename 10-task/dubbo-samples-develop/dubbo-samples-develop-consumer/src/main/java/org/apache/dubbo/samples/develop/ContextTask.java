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
package org.apache.dubbo.samples.develop;


import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;

import com.alibaba.fastjson2.JSON;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContextTask implements CommandLineRunner {
    @DubboReference
    private ContextService contextService;

    @Override
    public void run(String... args) throws Exception {
        //往服务端传递参数
        RpcContext.getClientAttachment().setAttachment("clientKey1","clientValue1");
        String res = contextService.invoke("context1");
        //接收传递回来参数
        Map<String, Object> clientAttachment = RpcContext.getServerContext().getObjectAttachments();
        System.out.println("ContextTask clientAttachment:" + JSON.toJSONString(clientAttachment));
        System.out.println("ContextService Return : " + res);
    }
}