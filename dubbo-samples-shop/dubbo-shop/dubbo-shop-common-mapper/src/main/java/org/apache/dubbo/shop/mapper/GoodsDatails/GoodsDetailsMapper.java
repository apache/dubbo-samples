package org.apache.dubbo.shop.mapper.GoodsDatails;

import org.apache.dubbo.shop.common.pojo.GoodsDetails.GoodsDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsDetailsMapper {
    public GoodsDetails getGoodsDetails(Integer id);
}
