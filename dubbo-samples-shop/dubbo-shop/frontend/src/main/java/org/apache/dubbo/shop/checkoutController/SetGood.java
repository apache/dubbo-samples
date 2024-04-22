package org.apache.dubbo.shop.checkoutController;

import org.apache.dubbo.shop.attributeClass.checkout.Good;
import org.apache.dubbo.shop.attributeClass.checkout.Summary;

public class SetGood {
    public static Good[] setgood(){
        Good[] goods = new Good[1];
        Good good = new Good();
        good.setId("1");
        good.setCount(1);
        good.setName("toukui");
        good.setPicture("/assets/images/liuying.png");
        good.setPrice("190");
        good.setAttrsText("好货");
        good.setSkuId("1");
        good.setTotalPayPrice("190");
        good.setPayPrice("190");
        good.setTotalPrice("190");
        goods[0] = good;
        return goods;
    }
    public static Summary setSummary(){
        Summary summary = new Summary();
        summary.setGoodsCount(1);
        summary.setTotalPrice(190);
        summary.setDiscountPrice(0);
        summary.setPostFee(100);
        return  summary;
    }
}
