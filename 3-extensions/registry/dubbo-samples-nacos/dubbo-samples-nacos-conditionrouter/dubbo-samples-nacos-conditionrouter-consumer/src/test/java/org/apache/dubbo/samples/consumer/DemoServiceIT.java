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

package org.apache.dubbo.samples.consumer;

import org.apache.dubbo.samples.governance.api.DemoService;
import org.apache.dubbo.samples.governance.api.DemoService2;
import org.apache.dubbo.samples.governance.consumer.ConsumerApplication;
import org.apache.dubbo.samples.governance.util.NacosUtils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {ConsumerApplication.class})
@RunWith(SpringRunner.class)
public class DemoServiceIT {
    @Autowired
    private DemoService demoService;

    @Autowired
    private DemoService2 demoService2;

    @BeforeClass
    public static void setUp() throws Throwable {
        NacosUtils.writeAppRule();
        Thread.sleep(2000);
    }

    @Test
    public void testDemoService() throws Exception {
        String result = demoService.sayHello("world");
        Assert.assertTrue(result.contains("20880"));
    }

    @Test
    public void testDemoService2() throws Exception {
        String result = demoService2.sayHello("world");
        Assert.assertTrue(result.contains("20881"));
    }
}
