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
package org.apache.dubbo.samples.frontend;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.DetailService;
import org.apache.dubbo.samples.Item;
import org.apache.dubbo.samples.Order;
import org.apache.dubbo.samples.OrderDetail;
import org.apache.dubbo.samples.OrderService;
import org.apache.dubbo.samples.ShopService;
import org.apache.dubbo.samples.User;
import org.apache.dubbo.samples.UserService;

import org.springframework.stereotype.Component;

@Component
public class ShopServiceImpl implements ShopService {
    @DubboReference(check = false, retries = 0)
    private UserService userService;

    @DubboReference(check = false)
    private OrderService orderService;

    @DubboReference(check = false)
    private DetailService detailService;

    @Override
    public boolean register(String username, String password, String realName, String mail, String phone) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRealName(realName);
        user.setMail(mail);
        user.setPhone(phone);
        return userService.register(user);
    }

    @Override
    public boolean login(String username, String password) {
        return userService.login(username, password) != null;
    }

    @Override
    public User getUserInfo(String username) {
        return userService.getInfo(username);
    }

    @Override
    public boolean timeoutLogin(String username, String password) {
        return userService.timeoutLogin(username, password) != null;
    }

    @Override
    public Item checkItem(long sku, String username) {
        return detailService.getItem(sku, username);
    }

    @Override
    public Item checkItemGray(long sku, String username) {
        return detailService.getItem(sku, username);
    }

    @Override
    public OrderDetail submitOrder(long sku, int count, String address, String phone, String receiver) {
        Order order = new Order();
        order.setSku(sku);
        order.setCount(count);
        order.setAddress(address);
        order.setPhone(phone);
        order.setReceiver(receiver);
        return orderService.submitOrder(order);
    }
}
