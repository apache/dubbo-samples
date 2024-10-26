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

package org.apache.dubbo.shop.service;

import org.apache.dubbo.shop.common.dto.OrderPrep;
import org.apache.dubbo.shop.common.dto.request.PlaceOrderRequest;
import org.apache.dubbo.shop.common.dto.response.PlaceOrderResponse;
import org.apache.dubbo.shop.common.pojo.Address;
import org.apache.dubbo.shop.common.pojo.CartItem;
import org.apache.dubbo.shop.common.pojo.CreditCardInfo;
import org.apache.dubbo.shop.common.pojo.Money;
import org.apache.dubbo.shop.common.pojo.OrderItem;
import org.apache.dubbo.shop.common.pojo.OrderResult;

import java.util.List;

public interface CheckoutService {
    
    PlaceOrderResponse placeOrder(PlaceOrderRequest request);
    OrderPrep prepareOrderItemsAndShippingQuoteFromCart(String userId, String userCurrency, Address address);
    Money quoteShipping(Address address, List<CartItem> items);
    List<CartItem> getUserCart(String userId);
    void emptyUserCart(String userId);
    List<OrderItem> prepOrderItems(List<CartItem> items, String userCurrency);
    Money covertCurrency(Money from, String toCurrency);
    String changeCard(Money amount, CreditCardInfo paymentInfo);
    void sendOrderConfirmation(String email, OrderResult order);
    String shipOrder(Address address, List<CartItem> items);
}
