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

package org.apache.samples.sentinel;

import org.apache.samples.sentinel.consumer.ConsumerConfiguration;
import org.apache.samples.sentinel.consumer.FooServiceConsumer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConsumerConfiguration.class})
public class FooServiceIT {
    @Autowired
    private FooServiceConsumer consumer;

    @Test
    public void testFlowControl1() throws Exception {
        for (int i = 0; i < 10; i++) {
            consumer.sayHello("dubbo");
        }
    }

    @Test
    public void testFlowControl2() {
        for (int i = 0; i < 11; i++) {
            try {
                consumer.sayHello("dubbo");
            } catch (Throwable e) {
                Assert.assertTrue(e.getMessage().contains("SentinelBlockException: FlowException"));
            }
        }
    }
}
