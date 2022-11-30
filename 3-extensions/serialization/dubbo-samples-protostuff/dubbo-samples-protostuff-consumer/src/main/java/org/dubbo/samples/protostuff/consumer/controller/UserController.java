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

package org.dubbo.samples.protostuff.consumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;

import org.dubbo.samples.protostuff.api.IUserService;
import org.dubbo.samples.protostuff.domain.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @DubboReference(check = false)
    private IUserService userService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String insertUserInfo(String name) {
        UserInfo userInfo = userService.insertUserInfo(name);
        return userInfo.toString();
    }
}
