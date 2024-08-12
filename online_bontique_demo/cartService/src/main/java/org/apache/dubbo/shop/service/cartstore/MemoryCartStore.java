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

package org.apache.dubbo.shop.service.cartstore;

import org.apache.dubbo.shop.common.pojo.Cart;
import org.apache.dubbo.shop.common.pojo.CartItem;
import org.apache.dubbo.shop.service.CartStore;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemoryCartStore implements CartStore {
    
    private final Map<String, Map<String, Integer>> carts = new HashMap<>();
    @Override
    public void addItem(String userId, String productId, Integer quantity) {
        carts.computeIfAbsent(userId, k -> new HashMap<>())
                .merge(productId, quantity, Integer::sum);
    }
    
    @Override
    public void emptyCart(String userId) {
        carts.remove(userId);
    }
    
    @Override
    public Cart getCart(String userId) {
        Map<String, Integer> userCart = carts.getOrDefault(userId, new HashMap<>());
        List<CartItem> items = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : userCart.entrySet()) {
            items.add(new CartItem(entry.getKey(), entry.getValue()));
        }
        
        return new Cart(userId, items);
    }
}
