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

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.governance.api.DemoService;
import org.apache.dubbo.samples.governance.util.NacosUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoServiceIT {

    @Before
    public void beforeTest() {
        try {
            DubboBootstrap.reset();
        } catch (Throwable ignore) {
            // since Dubbo 2.7.11
        }
    }

    @Test(expected = RpcException.class)
    public void testWithoutRule() throws Throwable {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/dubbo-demo-consumer.xml");
        context.start();
        try {
            Thread.sleep(2000);
            DemoService demoService = (DemoService) context.getBean("demoService");
            demoService.sayHello("world", 3000);
        } finally {
            context.close();
        }
    }

    @Test
    public void testWithAppRuleWithSufficientTimeout() throws Throwable {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/dubbo-demo-consumer.xml");
        context.start();
        try {
            NacosUtils.writeAppRule();
            Thread.sleep(2000);
            DemoService demoService = (DemoService) context.getBean("demoService");
            Assert.assertTrue(demoService.sayHello("world", 1000).startsWith("Hello world"));
        } finally {
            NacosUtils.clearAppRule();
            context.close();
        }
    }
}
