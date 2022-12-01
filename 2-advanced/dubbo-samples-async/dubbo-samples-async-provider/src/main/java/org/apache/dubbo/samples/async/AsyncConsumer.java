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

package org.apache.dubbo.samples.async;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.async.api.AsyncService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AsyncConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/async-consumer.xml");
        context.start();

        RpcContext.getContext().setAttachment("consumer-key1", "consumer-value1");
        AsyncService asyncService = context.getBean("asyncService", AsyncService.class);

        System.out.println(asyncService.sayHello("async call request"));
    }
}
