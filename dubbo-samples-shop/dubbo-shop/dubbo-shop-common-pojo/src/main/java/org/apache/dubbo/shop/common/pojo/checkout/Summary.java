package org.apache.dubbo.shop.common.pojo.checkout;

import lombok.Data;

@Data
public class Summary {
    /**
     * 订单总折扣
     */
    private long discountPrice;
    /**
     * 订单总件数
     */
    private long goodsCount;
    /**
     * 订单总邮费
     */
    private long postFee;
    /**
     * 订单总价格实付
     */
    private long totalPayPrice;
    /**
     * 订单总价格
     */
    private long totalPrice;
}
