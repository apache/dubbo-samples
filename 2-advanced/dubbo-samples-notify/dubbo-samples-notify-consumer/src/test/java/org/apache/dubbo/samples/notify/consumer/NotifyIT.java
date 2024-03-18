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

package org.apache.dubbo.samples.notify.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.samples.notify.DemoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotifyIT {

    @DubboReference(timeout = 6000, methods = @Method(name = "sayHello", onreturn = "notify.onReturn", onthrow = "notify.onThrow"))
    private DemoService demoService;

    @Autowired
    private NotifyImpl notify;

    @Test
    public void testDemoService() {
        String result = demoService.sayHello(1);
        Assertions.assertEquals("demo1", result);
    }

    @Test
    public void testOnReturn() throws Exception {
        int id = 2;
        demoService.sayHello(id);
        for (int i = 0; i < 100; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }

        Assertions.assertEquals("demo2", notify.ret.get(id));
    }

    @Test
    public void testOnThrow() throws Exception {
        int id = 11;
        try {
            demoService.sayHello(id);
        } catch (Throwable t) {
            // ignore
        }

        for (int i = 0; i < 100; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }

        Assertions.assertTrue(notify.ret.get(id) instanceof RuntimeException);
        Exception e = (Exception) notify.ret.get(id);
        Assertions.assertEquals("exception from sayHello: too large id", e.getMessage());
    }
}
