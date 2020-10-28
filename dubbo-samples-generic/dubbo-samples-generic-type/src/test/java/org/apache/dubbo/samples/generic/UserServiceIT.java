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

package org.apache.dubbo.samples.generic;

import org.apache.dubbo.samples.generic.api.Params;
import org.apache.dubbo.samples.generic.api.User;
import org.apache.dubbo.samples.generic.api.UserService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/generic-type-consumer.xml"})
public class UserServiceIT {
    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", "Charles");
        Params params = new Params(map);
        User user = userService.get(params);
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("Charles", user.getName());
    }
}
