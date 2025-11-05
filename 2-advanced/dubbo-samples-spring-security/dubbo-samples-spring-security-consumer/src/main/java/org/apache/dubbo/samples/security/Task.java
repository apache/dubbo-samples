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
package org.apache.dubbo.samples.security;

import org.apache.dubbo.common.utils.Assert;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.security.api.UserService;
import org.apache.dubbo.samples.security.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class Task implements CommandLineRunner {

    @DubboReference
    private UserService userService;

    @Override
    public void run(String... args) {
        new Thread(()-> {
            int num = 0;
            while (true) {
                try {
                    Thread.sleep(5000);
                    test();
                    num++;
                    System.out.println("The number of program executions: " + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void test() {
        findByUsername();
        assertThrowable(()->{
            bindSecurityContext(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
            List<User> users = userService.queryAll();
            for (User user :users) {
                System.out.println("queryAll result: " + user);
            }

        }, ex-> ex instanceof RpcException && ((RpcException) ex).isAuthorization());
    }

    private void findByUsername() {
        bindSecurityContext(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));

        User user = userService.findByUsername("dubbo");
        System.out.println("findByUsername result: " + user);

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
