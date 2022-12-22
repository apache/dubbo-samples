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
package org.apache.dubbo.samples.test;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.sentinel.DemoService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerIT {
    @DubboReference
    private DemoService demoService;

    @Test
    public void testLimit() {
        int successCount = 0, failedCount = 0;
        for (int i = 0; i < 15; i++) {
            try {
                demoService.sayHello("dubbo");
                successCount += 1;
            } catch (RuntimeException ex) {
                if (ex.getMessage().contains("SentinelBlockException: FlowException")) {
                    failedCount += 1;
                } else {
                    Assertions.fail(ex);
                }
            }
        }

        Assert.assertEquals(10, successCount);
        Assert.assertEquals(5, failedCount);
    }
}
