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

package org.apache.dubbo.samples;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.action.GreetingServiceConsumer;
import org.apache.dubbo.samples.api.GreetingService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConsumerBootstrap.ConsumerConfiguration.class)
public class GreetingServiceIT {

    @DubboReference(version = "1.0.0", group = "*")
    private GreetingService greetingServiceWildcard;

    @DubboReference(version = "1.0.0", group = "1")
    private GreetingService greetingServiceGroup1;

    @DubboReference(version = "1.0.0", group = "2")
    private GreetingService greetingServiceGroup2;

    @Test
    public void test() throws Exception {
        Assert.assertTrue(greetingServiceGroup1.sayHello("nacos").contains("group 1"));
        Assert.assertTrue(greetingServiceGroup2.sayHello("nacos").contains("group 2"));

        boolean match1 = false, match2 = false;

        for (int i = 0; i < 100; i++) {
            if (greetingServiceWildcard.sayHello("nacos").contains("group 1")) {
                match1 = true;
            } else if (greetingServiceWildcard.sayHello("nacos").contains("group 2")) {
                match2 = true;
            }
            if (match1 && match2) {
                break;
            }
        }
        Assert.assertTrue(match1 && match2);
    }
}
