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

package org.apache.dubbo.samples.notify;


import org.apache.dubbo.samples.notify.api.DemoService;
import org.apache.dubbo.samples.notify.impl.NotifyImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/notify-consumer.xml"})
public class NotifyIT {
    @Autowired
    private DemoService demoService;

    @Autowired
    private NotifyImpl notify;

    @Test
    public void testDemoService() throws Exception {
        String result = demoService.sayHello(1);
        Assert.assertEquals("demo1", result);
    }

    @Test
    public void testOnReturn() throws Exception {
        int id = 2;
        demoService.sayHello(id);
        for (int i = 0; i < 10; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }

        Assert.assertEquals("demo2", notify.ret.get(id));
    }

    @Test
    public void testOnThrow() throws Exception {
        int id = 11;
        try {
            demoService.sayHello(id);
        } catch (Throwable t) {
            // ignore
        }

        for (int i = 0; i < 10; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }

        Assert.assertTrue(notify.ret.get(id) instanceof RuntimeException);
        Exception e = (Exception) notify.ret.get(id);
        Assert.assertEquals("exception from sayHello: too large id", e.getMessage());
    }
}
