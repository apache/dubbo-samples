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

package org.apache.dubbo.samples.rpc.nativethrift;

import org.apache.dubbo.samples.rpc.nativethrift.api.DemoService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/thrift-consumer.xml"})
public class DemoServiceIT {
    @Autowired
    private DemoService.Iface demoService;

    @Test
    public void test() throws Exception {
        Assert.assertTrue(demoService.echoBool(true));
        Assert.assertEquals((byte) 0x1b, demoService.echoByte((byte) 0x1b));
        Assert.assertEquals(2, demoService.echoDouble(2d), 0);
        Assert.assertEquals(3, demoService.echoI16((short) 3));
        Assert.assertEquals(4, demoService.echoI32(4));
        Assert.assertEquals(5, demoService.echoI64(5));
        Assert.assertEquals("hello", demoService.echoString("hello"));
    }
}
