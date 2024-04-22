package org.apache.dubbo.shop.common.pojo.checkout;

import lombok.Data;

@Data
public class UserAddress {
    /**
     * 收货详细地址
     */
    private String address;
    /**
     * 收货地址标签，用英文逗号分割
     */
    private String addressTags;
    /**
     * 收货地址-城市编码
     */
    private String cityCode;
    /**
     * 收货人-联系方式
     */
    private String contact;
    /**
     * 收货地址-地区编码
     */
    private String countyCode;
    /**
     * 收货完整地址
     */
    private String fullLocation;
    /**
     * 收货地址id
     */
    private String id;
    /**
     * 是否为默认，0是, 1不是
     */
    private long isDefault;
    /**
     * 收货方-邮政编码
     */
    private String postalCode;
    /**
     * 收货地址-省份编码
     */
    private String provinceCode;
    /**
     * 收货人-名字
     */
    private String receiver;
}
