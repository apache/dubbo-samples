package org.apache.dubbo.shop.web.hotGoods;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.shop.common.ReturnResult;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGoodsList;
import org.apache.dubbo.shop.service.NewGoodsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("good")
public class HotGoodsController {
    @DubboReference
    NewGoodsService newGoodsService;
    @GetMapping("hot")
    public ReturnResult newGoodsList(){
        NewGoodsList newGoodsList = newGoodsService.newGoodsListResult();
        return ReturnResult.success(newGoodsList);
    }
}
