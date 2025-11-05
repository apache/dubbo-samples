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

package org.apache.dubbo.shop.checkout;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.dto.OrderPrep;
import org.apache.dubbo.shop.common.dto.request.ChargeRequest;
import org.apache.dubbo.shop.common.dto.request.CurrencyConversionRequest;
import org.apache.dubbo.shop.common.dto.request.EmptyCartRequest;
import org.apache.dubbo.shop.common.dto.request.GetCartRequest;
import org.apache.dubbo.shop.common.dto.request.GetProductRequest;
import org.apache.dubbo.shop.common.dto.request.GetQuoteRequest;
import org.apache.dubbo.shop.common.dto.request.PlaceOrderRequest;
import org.apache.dubbo.shop.common.dto.request.ShipOrderRequest;
import org.apache.dubbo.shop.common.dto.response.ChargeResponse;
import org.apache.dubbo.shop.common.dto.response.GetQuoteResponse;
import org.apache.dubbo.shop.common.dto.response.PlaceOrderResponse;
import org.apache.dubbo.shop.common.dto.response.ShipOrderResponse;
import org.apache.dubbo.shop.common.pojo.Address;
import org.apache.dubbo.shop.common.pojo.Cart;
import org.apache.dubbo.shop.common.pojo.CartItem;
import org.apache.dubbo.shop.common.pojo.CreditCardInfo;
import org.apache.dubbo.shop.common.pojo.Money;
import org.apache.dubbo.shop.common.pojo.OrderItem;
import org.apache.dubbo.shop.common.pojo.OrderResult;
import org.apache.dubbo.shop.common.pojo.Product;
import org.apache.dubbo.shop.common.utils.MoneyUtils;
import org.apache.dubbo.shop.service.CartService;
import org.apache.dubbo.shop.service.CheckoutService;
import org.apache.dubbo.shop.service.CurrencyService;
import org.apache.dubbo.shop.service.EmailService;
import org.apache.dubbo.shop.service.PaymentService;
import org.apache.dubbo.shop.service.ProductCatalogService;
import org.apache.dubbo.shop.service.ShippingService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DubboService
@Service
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    @DubboReference
    private CartService cartService;
    @DubboReference
    private CurrencyService currencyService;
    @DubboReference
    private EmailService emailService;
    @DubboReference
    private PaymentService paymentService;
    @DubboReference
    private ProductCatalogService productCatalogService;
    @DubboReference
    private ShippingService shippingService;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
        log.info("[PlaceOrder] user_id={} user_currency={}", request.getUserId(), request.getUserCurrency());
        String orderId = UUID.randomUUID().toString();

        OrderPrep prep = prepareOrderItemsAndShippingQuoteFromCart(request.getUserId(), request.getUserCurrency(), request.getAddress());

        Money total = new Money(request.getUserCurrency(), 0L, 0);
        total = MoneyUtils.sum(total, prep.getShippingCostLocalized());
        for (OrderItem item : prep.getOrderItems()) {
            Money multPrice = MoneyUtils.multiplySlow(item.getCost(), item.getItem().getQuantity());
            total = MoneyUtils.sum(total, multPrice);
        }

        String txId = changeCard(total, request.getCreditCard());
        log.info("payment went through (transaction_id: {})", txId);

        String shipmentTrackingId = shipOrder(request.getAddress(), prep.getCartItems());

        emptyUserCart(request.getUserId());

        OrderResult orderResult = new OrderResult();
        orderResult.setOrderId(orderId);
        orderResult.setShippingTrackingId(shipmentTrackingId);
        orderResult.setShippingCost(prep.getShippingCostLocalized());
        orderResult.setShippingAddress(request.getAddress());
        orderResult.setItems(prep.getOrderItems());

        sendOrderConfirmation(request.getEmail(), orderResult);

        return new PlaceOrderResponse(orderResult);
    }

    @Override
    public OrderPrep prepareOrderItemsAndShippingQuoteFromCart(String userId, String userCurrency, Address address) {
        OrderPrep out = new OrderPrep();
        List<CartItem> cartItems = getUserCart(userId);
        List<OrderItem> orderItems = prepOrderItems(cartItems, userCurrency);
        Money shippingUSD = quoteShipping(address, cartItems);
        Money shippingPrice = covertCurrency(shippingUSD, userCurrency);

        out.setShippingCostLocalized(shippingPrice);
        out.setOrderItems(orderItems);
        out.setCartItems(cartItems);

        return out;
    }

    @Override
    public Money quoteShipping(Address address, List<CartItem> items) {
        GetQuoteRequest request = new GetQuoteRequest();
        request.setAddress(address);
        request.setItems(items);

        GetQuoteResponse response = shippingService.getQuote(request);
        return response.getCostUsd();
    }

    @Override
    public List<CartItem> getUserCart(String userId) {
        Cart cart = cartService.getCart(userId);
        return cart.getItems();
    }

    @Override
    public void emptyUserCart(String userId) {
        EmptyCartRequest request = new EmptyCartRequest();
        request.setUserId(userId);
        cartService.emptyCart(userId);
    }

    @Override
    public List<OrderItem> prepOrderItems(List<CartItem> items, String userCurrency) {
        List<OrderItem> out = new ArrayList<>();
        for (CartItem item : items) {
            GetProductRequest request = new GetProductRequest();
            request.setId(item.getProductId());

            Product product = productCatalogService.getProduct(request);
            Money price = covertCurrency(product.getPriceUsd(), userCurrency);

            OrderItem orderItem = new OrderItem();
            orderItem.setCost(price);
            orderItem.setItem(item);

            out.add(orderItem);
        }
        return out;
    }

    @Override
    public Money covertCurrency(Money from, String toCurrency) {
        CurrencyConversionRequest request = new CurrencyConversionRequest();
        request.setFrom(from);
        request.setToCode(toCurrency);

        return currencyService.convert(request);
    }

    @Override
    public String changeCard(Money amount, CreditCardInfo paymentInfo) {
        ChargeRequest request = new ChargeRequest();
        request.setAmount(amount);
        request.setCreditCard(paymentInfo);

        ChargeResponse response = paymentService.charge(request);
        return response.getTransactionId();
    }

    @Override
    public void sendOrderConfirmation(String email, OrderResult order) {
        emailService.sendOrderConfirmation(email, order.getOrderId());
    }

    @Override
    public String shipOrder(Address address, List<CartItem> items) {
        ShipOrderRequest request = new ShipOrderRequest();
        request.setAddress(address);
        request.setItems(items);

        ShipOrderResponse response = shippingService.shipOrder(request);
        return response.getTrackingId();
    }
}
