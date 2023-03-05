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
package org.apache.dubbo.springboot.hibernate.samples.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.springboot.hibernate.samples.HibernateService;
import org.apache.dubbo.springboot.hibernate.samples.User;
import org.apache.dubbo.springboot.hibernate.samples.repository.UserRepository;
import org.apache.dubbo.springboot.hibernate.samples.repository.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@DubboService
public class HibernateServiceImpl implements HibernateService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("apache dubbo");
        userRepository.saveAndFlush(userModel);

        List<UserModel> userModels = userRepository.findAll();
        return userModels.stream().map(u -> {
            User user = new User();
            user.setId(u.getId());
            user.setName(u.getName());
            return user;
        }).collect(Collectors.toList());
    }
}
