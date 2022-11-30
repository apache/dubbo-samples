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

package org.apache.dubbo.samples.group;

import org.apache.dubbo.samples.group.api.GroupService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/group-consumer.xml"})
public class GroupServiceIT {
    @Autowired
    @Qualifier("groupAService")
    private GroupService groupServiceA;

    @Autowired
    @Qualifier("groupBService")
    private GroupService groupServiceB;

    @Test
    public void testServiceA() throws Exception {
        String result = groupServiceA.sayHello("world");
        Assert.assertTrue(result.startsWith("Hello world"));
        Assert.assertTrue(result.contains("group A"));
    }

    @Test
    public void testServiceB() throws Exception {
        String result = groupServiceB.sayHello("world");
        Assert.assertTrue(result.startsWith("Hello world"));
        Assert.assertTrue(result.contains("group B"));
    }
}
