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
import org.apache.dubbo.samples.DetailService;
import org.apache.dubbo.samples.Item;
import org.apache.dubbo.samples.Order;
import org.apache.dubbo.samples.OrderService;
import org.apache.dubbo.samples.ShopService;
import org.apache.dubbo.samples.User;
import org.apache.dubbo.samples.UserService;

@DubboService
public class ShopServiceImpl implements ShopService {
    @DubboReference
    private UserService userService;

    @DubboReference
    private OrderService orderService;

    @DubboReference
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
    public boolean createItem(long sku, String itemName, String description, int stock) {
        Item item = new Item();
        item.setSku(sku);
        item.setItemName(itemName);
        item.setDescription(description);
        item.setStock(stock);
        return detailService.createItem(item);
    }

    @Override
    public boolean submitOrder(long sku, int count, String address, String phone, String receiver) {
        Order order = new Order();
        order.setSku(sku);
        order.setCount(count);
        order.setAddress(address);
        order.setPhone(phone);
        order.setReceiver(receiver);
        return orderService.submitOrder(order);
    }
}
