/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.dubbo.samples.rest;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rest.api.User;
import org.apache.dubbo.samples.rest.api.facade.AnotherUserRestService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/rest-consumer.xml"})
public class DemoConsumerTomcatTestIT {
    @Autowired
    AnotherUserRestService userService;

    @Test
    public void register() {
        User user = new User(1L, "larrypage");
        Assert.assertNotNull(userService.registerUser(user).getId());
    }

    @Test
    public void getUser() {
        User user = userService.getUser(1L);
        Assert.assertEquals(1L, user.getId().longValue());
        Assert.assertEquals("username1", user.getName());
    }

    @Test
    public void context() {
        RpcContext.getContext().setAttachment("clientName", "demo");
        RpcContext.getContext().setAttachment("clientImpl", "dubbo");

        String expect = "Client name is demo\n" +
                "Client impl is dubbo";
        Assert.assertEquals(expect, userService.getContext());
    }
}
