package org.apache.dubbo.shop.common.pojo.NewGoodsList;

import lombok.Data;

@Data
public class NewGood {
    /**
     * id
     */
    private String id;
    /**
     * 图片
     */
    private String picture;
    /**
     * 名字
     */
    private String name;
    /**
     * 价格
     */
    private String price;
}
