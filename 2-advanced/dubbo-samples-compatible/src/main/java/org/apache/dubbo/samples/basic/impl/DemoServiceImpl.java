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

package org.apache.dubbo.samples.basic.impl;

import org.apache.dubbo.samples.basic.api.DemoService;
import org.apache.dubbo.samples.basic.api.Phone;
import org.apache.dubbo.samples.basic.api.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.dubbo.rpc.RpcContext.getContext;

public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + getContext().getLocalAddress();
    }

    @Override
    public User getUser(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("id is 0");
        }

        User user = new User();
        Phone phone = new Phone();
        user.setName("aaa");
        user.setAge(10);
        phone.setMobile("13333333");
        phone.setTel("057199999");
        user.setPhone(phone);
        return user;
    }
}
