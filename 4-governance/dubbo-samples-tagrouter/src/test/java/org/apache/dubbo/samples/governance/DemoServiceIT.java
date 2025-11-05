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

package org.apache.dubbo.samples.governance;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.governance.api.DemoService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.apache.dubbo.common.constants.CommonConstants.TAG_KEY;
import static org.apache.dubbo.rpc.Constants.FORCE_USE_TAG;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/dubbo-demo-consumer.xml"})
public class DemoServiceIT {
    @Autowired
    @Qualifier("demoService")
    private DemoService demoService;

    @BeforeClass
    public static void setUp() throws Exception {
        RuleUtil.initClient();
        RuleUtil.generateRule();
        Thread.sleep(1000);
    }

    @Test
    public void testDemoService1() throws Exception {
        for (int i = 0; i < 10; i++) {
            RpcContext.getContext().setAttachment(FORCE_USE_TAG, "true");
            RpcContext.getContext().setAttachment(TAG_KEY, "tag1");
            Assert.assertTrue(demoService.sayHello("world").contains("20881"));
        }
    }

    @Test
    public void testDemoService2() throws Exception {
        for (int i = 0; i < 10; i++) {
            RpcContext.getContext().setAttachment(FORCE_USE_TAG, "true");
            RpcContext.getContext().setAttachment(TAG_KEY, "tag2");
            Assert.assertTrue(demoService.sayHello("world").contains("20880"));
        }
    }

    @Test(expected = RpcException.class)
    public void testDemoService3() throws Exception {
        RpcContext.getContext().setAttachment(TAG_KEY, "tag3");
        demoService.sayHello("world");
    }
}
