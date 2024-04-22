package org.apache.dubbo.shop.service.goodsDetails;


import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.mapper.GoodsDatails.GoodsDetailsMapper;
import org.apache.dubbo.shop.service.GoodsDetails;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class GoodsDatailServiceImpl implements GoodsDetails {
    @Autowired
    GoodsDetailsMapper goodsDetailsMapper;
    @Override
    public GoodsDetails GoodsResult(Integer id) {
        return null;
    }
}
