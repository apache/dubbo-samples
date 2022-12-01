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

/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.samples.boundary.hibernate.impl;

import org.apache.dubbo.samples.boundary.hibernate.api.HibernateService;
import org.apache.dubbo.samples.boundary.hibernate.api.User;
import org.apache.dubbo.samples.boundary.hibernate.impl.dao.UserDao;
import org.apache.dubbo.samples.boundary.hibernate.impl.dao.UserModel;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class HibernateServiceImpl implements HibernateService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        List<UserModel> userModels = userDao.findAll();

        return userModels.stream().map(u -> {
            User user = new User();

            user.setId(u.getId());
            user.setName(u.getName());

            return user;
        }).collect(Collectors.toList());
    }
}
