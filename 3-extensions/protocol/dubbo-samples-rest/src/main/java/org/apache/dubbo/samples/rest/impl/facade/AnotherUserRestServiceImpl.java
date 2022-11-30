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
package org.apache.dubbo.samples.rest.impl.facade;

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.rest.api.User;
import org.apache.dubbo.samples.rest.api.UserService;
import org.apache.dubbo.samples.rest.api.facade.AnotherUserRestService;
import org.apache.dubbo.samples.rest.api.facade.RegistrationResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("anotherUserRestService")
public class AnotherUserRestServiceImpl implements AnotherUserRestService {

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getUser(Long id) {
        System.out.println("Client name is " + RpcContext.getContext().getAttachment("clientName"));
        System.out.println("Client impl is " + RpcContext.getContext().getAttachment("clientImpl"));
        return userService.getUser(id);
    }

    @Override
    public RegistrationResult registerUser(User user) {
        return new RegistrationResult(userService.registerUser(user));
    }

    @Override
    public String getContext() {
        return "Client name is " + RpcContext.getContext().getAttachment("clientName") + "\n" +
                "Client impl is " + RpcContext.getContext().getAttachment("clientImpl");
    }
}
