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

package org.apache.dubbo.shop.service.cart;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.pojo.Cart;
import org.apache.dubbo.shop.common.pojo.CartItem;
import org.apache.dubbo.shop.service.CartService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
@Service
public class CartServiceImpl implements CartService {

    private final Map<String, List<CartItem>> cartStore = new HashMap<>();
    @Override
    public void addItem(String userId, CartItem newItem) {
        // Retrieve the list of cart items for the user, create a new list if it doesn't exist
        List<CartItem> cartItems = cartStore.computeIfAbsent(userId, k -> new ArrayList<>());

        // Flag to check if the item was updated
        boolean itemUpdated = false;

        // Iterate through the list to check if the item already exists
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(newItem.getProductId())) {
                // If the item exists, update the quantity
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                itemUpdated = true;
                break;
            }
        }

        // If the item was not updated, add it as a new item
        if (!itemUpdated) {
            cartItems.add(newItem);
        }

        // Update the cartStore with the new list
        cartStore.put(userId, cartItems);
    }

    @Override
    public Cart getCart(String userId) {
        List<CartItem> items = cartStore.getOrDefault(userId, new ArrayList<>());
        return new Cart(userId, items);
    }

    @Override
    public void emptyCart(String userId) {
        cartStore.remove(userId);
    }
}
