package org.apache.dubbo.shop.mapper.NewGoods;

import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGood;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGoodsList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Configuration;


@Mapper
public interface NewGoodsMapper {
    @Select("select * from newgoods where id = #{orderId}")
    public NewGood getNewGoods(Integer orderId);
}
