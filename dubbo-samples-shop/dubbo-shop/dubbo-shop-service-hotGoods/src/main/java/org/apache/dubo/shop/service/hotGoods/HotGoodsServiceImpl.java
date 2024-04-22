package org.apache.dubo.shop.service.hotGoods;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.shop.common.pojo.HotGoodsList.HotGoodsList;
import org.apache.dubbo.shop.mapper.HotGoods.HotGoodsMapper;
import org.apache.dubbo.shop.mapper.NewGoods.NewGoodsMapper;
import org.apache.dubbo.shop.service.HotGoodsService;

public class HotGoodsServiceImpl implements HotGoodsService {
    @DubboReference
    HotGoodsMapper hotGoodsMapper;
    @Override
    public HotGoodsList hotGoodsList() {
        return null;
    }
}
