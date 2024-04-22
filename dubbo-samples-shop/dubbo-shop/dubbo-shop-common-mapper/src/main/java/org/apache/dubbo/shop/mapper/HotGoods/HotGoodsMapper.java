package org.apache.dubbo.shop.mapper.HotGoods;

import org.apache.dubbo.shop.common.pojo.HotGoodsList.HotGoodsList;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGoodsList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotGoodsMapper {
    public HotGoodsList getHotGoodsList();
}
