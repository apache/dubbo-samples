package org.apache.dubbo.shop.common.pojo.checkout;

import lombok.Data;

@Data
public class CheckoutData {
    /**
     * 订单商品集合
     */
    private Good[] goods;
    /**
     * 订单总计信息
     */
    private Summary summary;
    /**
     * 订单内用户地址列表
     */
    private UserAddress[] userAddresses;
}
