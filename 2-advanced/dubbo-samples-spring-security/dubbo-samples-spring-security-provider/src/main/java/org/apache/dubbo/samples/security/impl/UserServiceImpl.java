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

package org.apache.dubbo.samples.security.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.security.api.UserService;
import org.apache.dubbo.samples.security.model.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;

@DubboService
public class UserServiceImpl implements UserService {

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User findByUsername(String username) {
        User user = new User();
        user.setGender("male");
        user.setUserName(username);
        user.setId("123456");

        return user;
    }

    @Override
    @Secured("ROLE_USER")
    public List<User> queryAll() {
        List<User> result = new ArrayList<>();
        result.add(new User());
        return result;
    }
}
