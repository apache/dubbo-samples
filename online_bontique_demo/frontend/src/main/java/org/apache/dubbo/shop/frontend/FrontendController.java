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

package org.apache.dubbo.shop.frontend;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.shop.common.dto.request.AdRequest;
import org.apache.dubbo.shop.common.dto.request.AddItemRequest;
import org.apache.dubbo.shop.common.dto.request.GetProductRequest;
import org.apache.dubbo.shop.common.dto.request.GetQuoteRequest;
import org.apache.dubbo.shop.common.dto.request.ListRecommendationsRequest;
import org.apache.dubbo.shop.common.dto.request.PlaceOrderRequest;
import org.apache.dubbo.shop.common.dto.response.ListProductsResponse;
import org.apache.dubbo.shop.common.dto.response.ListRecommendationsResponse;
import org.apache.dubbo.shop.common.pojo.Ad;
import org.apache.dubbo.shop.common.pojo.Address;
import org.apache.dubbo.shop.common.pojo.Cart;
import org.apache.dubbo.shop.common.pojo.CartItem;
import org.apache.dubbo.shop.common.pojo.Empty;
import org.apache.dubbo.shop.common.pojo.Money;
import org.apache.dubbo.shop.common.pojo.Product;
import org.apache.dubbo.shop.common.utils.MoneyUtils;
import org.apache.dubbo.shop.service.AdsService;
import org.apache.dubbo.shop.service.CartService;
import org.apache.dubbo.shop.service.CheckoutService;
import org.apache.dubbo.shop.service.CurrencyService;
import org.apache.dubbo.shop.service.EmailService;
import org.apache.dubbo.shop.service.PaymentService;
import org.apache.dubbo.shop.service.ProductCatalogService;
import org.apache.dubbo.shop.service.RecommendationService;
import org.apache.dubbo.shop.service.ShippingService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class FrontendController {
    
    @DubboReference
    private ShippingService shippingService;
    @DubboReference
    private CartService cartService;
    @DubboReference
    private RecommendationService recommendationService;
    @DubboReference
    private ProductCatalogService productCatalogService;
    @DubboReference
    private CurrencyService currencyService;
    @DubboReference
    private PaymentService paymentService;
    @DubboReference
    private EmailService emailService;
    @DubboReference
    private CheckoutService checkoutService;
    @DubboReference
    private AdsService adsService;
    
    @PostMapping("/cart/add")
    public String addItemToCart(@RequestParam String productId, @RequestParam Integer quantity, @RequestParam String userId) {
        CartItem item = new CartItem(productId, quantity);
        AddItemRequest request = new AddItemRequest(userId, item);
        cartService.addItem(request.getUserId(), request.getItem());
        return "redirect:/cart"; // 重定向到购物车页面或其他页面
    }
    
    @GetMapping("/cart")
    public String getCart(Model model, String userId) throws ExecutionException, InterruptedException {
        model.addAttribute("is_cymbal_brand", false);
        model.addAttribute("show_currency", false);
        Cart cart = cartService.getCart("1");
        model.addAttribute("items", cart);
        List<CartItem> items = cart.getItems();
        
        Map<Product, Integer> productQuantityMap = new HashMap<>();
        int totalQuantity = 0;
        
        List<String> productIds = new ArrayList<>();
        for (CartItem item : items) {
            totalQuantity += item.getQuantity();
            Product product = productCatalogService.getProduct(new GetProductRequest(item.getProductId()));
            productIds.add(item.getProductId());
            if (product != null) {
                productQuantityMap.put(product, item.getQuantity());
            }
        }
        
        Money totalCost = new Money("USD", 0L, 0);
        for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
            totalCost = MoneyUtils.sum(totalCost, MoneyUtils.multiplySlow(entry.getKey().getPriceUsd(), entry.getValue()));
        }
        
        ListRecommendationsResponse recommendations = recommendationService.listRecommendations(new ListRecommendationsRequest("1", productIds));
        List<Product> products = new ArrayList<>();
        for (String productId : recommendations.getProductIds()) {
            products.add(productCatalogService.getProduct(new GetProductRequest(productId)));
        }
        model.addAttribute("recommendations", products);
        model.addAttribute("cart_size", totalQuantity);
        model.addAttribute("productQuantityMap", productQuantityMap);
        model.addAttribute("total_cost", totalCost);
        model.addAttribute("shipping_cost", shippingService.getQuote(new GetQuoteRequest(new Address(), items)));
        
        return "cart";
    }
    
    @PostMapping("/cart/empty")
    public String emptyCart(String userId) {
        cartService.emptyCart("1");
        return "redirect:/cart";
    }
    
    @PostMapping("/cart/checkout")
    public String checkout(String userId) {
        cartService.emptyCart("1");
        return "redirect:/static";
    }
    
    @GetMapping("/products")
    public String listProducts(Model model) {
        ListProductsResponse response = productCatalogService.listProducts(new Empty());
        model.addAttribute("is_cymbal_brand", false);
        model.addAttribute("show_currency", false);
        model.addAttribute("products", response.getProducts());
        return "product";
    }
    @GetMapping("/order")
    public String placeOrder(Model model) {
        model.addAttribute("is_cymbal_brand", false);
        model.addAttribute(checkoutService.placeOrder(new PlaceOrderRequest()).getOrder());
        return "order";
    }
    
    @GetMapping("/ad")
    public String getAds(@RequestParam List<String> contextKeys, Model model) {
        AdRequest request = new AdRequest(contextKeys);
        List<Ad> ads = adsService.getAds(request).getAds();
        model.addAttribute("ads", ads);
        return "ad";
    }
    
    @GetMapping({"/static"})
    public String listUser(Model model) {
        model.addAttribute("is_cymbal_brand", false);
        ListProductsResponse response = productCatalogService.listProducts(new Empty());
        model.addAttribute("products", response.getProducts());
        return "home";
    }
}
