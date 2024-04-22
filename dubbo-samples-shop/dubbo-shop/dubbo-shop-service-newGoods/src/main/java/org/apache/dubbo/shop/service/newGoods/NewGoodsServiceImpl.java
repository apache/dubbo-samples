package org.apache.dubbo.shop.service.newGoods;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGood;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGoodsList;
import org.apache.dubbo.shop.mapper.NewGoods.NewGoodsMapper;
import org.apache.dubbo.shop.service.NewGoodsService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class NewGoodsServiceImpl implements NewGoodsService {
    @Autowired
    NewGoodsMapper newGoodsMapper;
    @Override
    public NewGoodsList newGoodsListResult() {

        NewGood newGood = newGoodsMapper.getNewGoodsList();
        System.out.println(newGood);
        return null;
    }
}
