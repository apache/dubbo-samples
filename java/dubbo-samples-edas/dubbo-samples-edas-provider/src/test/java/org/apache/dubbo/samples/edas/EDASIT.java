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

package org.apache.dubbo.samples.edas;

import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.edas.provider.DubboProvider;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DubboProvider.class, DubboConsumer.class})
public class EDASIT {
    @Autowired
    private DubboConsumer dubboConsumer;

    @Test
    public void testGreeting() throws Exception {
        // FIXME, no provider
        try {
            System.out.println(dubboConsumer.callDemoService());
        } catch (Exception e) {
            Assert.assertTrue(e instanceof RpcException);
            Assert.assertTrue(((RpcException) e).getMessage().contains("No provider available"));
        }
    }
}

