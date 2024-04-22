package org.apache.dubbo.shop.service;

import org.apache.dubbo.shop.common.pojo.checkout.CheckoutData;

public interface CheckoutService {
    public CheckoutData checkoutResult(Integer orderId);
}

