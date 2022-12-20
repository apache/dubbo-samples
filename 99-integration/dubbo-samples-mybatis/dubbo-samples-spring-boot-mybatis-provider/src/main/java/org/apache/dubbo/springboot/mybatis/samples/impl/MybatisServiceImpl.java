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

package org.apache.dubbo.springboot.mybatis.samples.impl;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.springboot.mybatis.samples.MybatisService;
import org.apache.dubbo.springboot.mybatis.samples.User;
import org.apache.dubbo.springboot.mybatis.samples.dao.UserDao;
import org.apache.dubbo.springboot.mybatis.samples.dao.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DubboService(version = "1.0.0")
public class MybatisServiceImpl implements MybatisService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUserId(Long id) {
        UserModel userModel = userDao.findByUserId(id);
        User user = new User();
        user.setId(userModel.getId());
        user.setName(userModel.getName());
        return user;
    }
}
