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

package org.apache.dubbo.samples.client;

import org.apache.dubbo.common.utils.Assert;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.api.UserService;
import org.apache.dubbo.samples.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class UserServiceClient implements SmartInitializingSingleton {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceClient.class);
    @DubboReference
    private UserService userService;

    @Override
    public void afterSingletonsInstantiated() {
        findByUsername();
        assertThrowable(()->{
            bindSecurityContext(null);
            userService.queryAll();
        }, ex-> ex instanceof RpcException && ((RpcException) ex).isAuthorization());
    }

    private void findByUsername() {
        bindSecurityContext(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));

        User user = userService.findByUsername("dubbo");

        LOG.info("result {} ",user);

        Assert.notNull(user, "user is null");
    }

    private void bindSecurityContext(List<GrantedAuthority> authorityList) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                "zs", "123456", authorityList);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    public void assertThrowable(Runnable runnable, Predicate<Exception> throwable){
        try{
            runnable.run();
        }catch(Exception ex){
            boolean result = throwable.test(ex);
            Assert.assertTrue(result, String.format("test error %s", ex.getMessage()));
        }
    }
}
