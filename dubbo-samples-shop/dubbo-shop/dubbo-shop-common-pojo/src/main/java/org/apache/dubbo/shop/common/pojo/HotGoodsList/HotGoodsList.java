package org.apache.dubbo.shop.common.pojo.HotGoodsList;

import lombok.Data;

@Data
public class HotGoodsList {
    /**
     * 推荐别名
     */
    private String alt;
    /**
     * 推荐id
     */
    private String id;
    /**
     * 推荐图片
     */
    private String picture;
    /**
     * 推荐标题
     */
    private String title;
}
