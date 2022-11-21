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

package org.apache.dubbo.samples.local;

import org.apache.dubbo.samples.local.api.DemoService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/dubbo-demo.xml"})
public class DemoServiceIT {
    @Autowired
    private DemoService demoService;

    @BeforeClass
    public static void setUp() throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
    }

    @Test
    public void test() throws Exception {
        // see also: org.apache.dubbo.rpc.protocol.injvm.InjvmInvoker.doInvoke
        // InjvmInvoker set remote address to 127.0.0.1:0
        String result = demoService.sayHello("world");
        Assert.assertEquals(result, "Hello world, response from provider: 127.0.0.1:0");

        result = demoService.sayHelloAsync("world");
        Assert.assertEquals(result, "Hello world, response from provider: 127.0.0.1:0");

    }

}
