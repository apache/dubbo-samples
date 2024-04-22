package org.apache.dubbo.shop.mapper.Checkout;


import org.apache.dubbo.shop.common.pojo.checkout.CheckoutData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckoutServiceMapper {
    public CheckoutData getCheckoutData(Integer orderId);
}
