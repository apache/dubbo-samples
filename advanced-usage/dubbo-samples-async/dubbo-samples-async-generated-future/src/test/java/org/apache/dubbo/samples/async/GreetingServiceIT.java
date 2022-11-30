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

package org.apache.dubbo.samples.async;

import org.apache.dubbo.samples.async.api.GreetingService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CompletableFuture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/async-consumer.xml"})
public class GreetingServiceIT {
    @Autowired
    private GreetingService greetingService;

    @Test
    public void testAsync() throws Exception {
        CompletableFuture<String> future = greetingService.greeting("async call request", (byte) 0x01);
        Assert.assertEquals("Fine, async call request", future.get());
    }

    @Test
    public void testSync() throws Exception {
        Assert.assertEquals("Fine, sync call request", greetingService.greeting("sync call request"));
    }
}
