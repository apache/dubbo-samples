package org.apache.duubo.shop.service.checkout;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.pojo.checkout.CheckoutData;
import org.apache.dubbo.shop.service.CheckoutService;
import org.apache.dubbo.shop.mapper.Checkout.CheckoutServiceMapper;

@DubboService
public class CheckoutServiceImpl implements CheckoutService{
    @DubboReference
    CheckoutServiceMapper checkoutServiceMapper;
    @Override
    public CheckoutData checkoutResult(Integer orderId) {
        CheckoutData checkoutData = checkoutServiceMapper.findCheckoutData(orderId);
        return checkoutData;
    }
}
