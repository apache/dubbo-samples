package org.apache.dubbo.shop.common.pojo.checkout;

import lombok.Data;

@Data
public class Good {
    /**
     * 商品属性
     */
    private String attrsText;
    /**
     * 商品数量，购买数量
     */
    private long count;
    /**
     * 商品id
     */
    private String id;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 商品实付单价
     */
    private String payPrice;
    /**
     * 商品图片
     */
    private String picture;
    /**
     * 商品价格
     */
    private String price;
    /**
     * 商品sku的id
     */
    private String skuId;
    /**
     * 商品实付价格小计
     */
    private String totalPayPrice;
    /**
     * 商品小计总价
     */
    private String totalPrice;
}
