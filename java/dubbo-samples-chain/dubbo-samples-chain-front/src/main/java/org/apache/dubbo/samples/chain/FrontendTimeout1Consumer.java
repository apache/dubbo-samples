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

package org.apache.dubbo.samples.chain;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.chain.api.AmericanService;
import org.apache.dubbo.samples.chain.api.CatService;
import org.apache.dubbo.samples.chain.api.ChineseService;
import org.apache.dubbo.samples.chain.api.DogService;
import org.apache.dubbo.samples.chain.api.LionService;
import org.apache.dubbo.samples.chain.api.TigerService;
import org.apache.dubbo.samples.chain.api.TimeoutCountDownService1;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.dubbo.common.constants.CommonConstants.TAG_KEY;

public class FrontendTimeout1Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-demo-consumer.xml");
        context.start();

        TimeoutCountDownService1 timeoutCountDownService1 = (TimeoutCountDownService1) context.getBean("timeoutCountdownService1");
        System.out.println("result: " + timeoutCountDownService1.testTieout());
    }
}
