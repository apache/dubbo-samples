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

package org.apache.dubbo.samples.generic.call;

import org.apache.dubbo.samples.generic.call.api.HelloService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/generic-impl-consumer.xml"})
public class HelloServiceIT {
    @Autowired
    private HelloService helloService;

    @Test(expected = RuntimeException.class)
    public void testHello() {
        helloService.sayHello("world");
    }

    @Test
    public void testHelloAsync() throws Exception {
        Assert.assertEquals("sayHelloAsync: hello world", helloService.sayHelloAsync("world").get());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNotImplementedHello() throws Exception {
        helloService.notImplementedHello("dubbo");
    }
}
