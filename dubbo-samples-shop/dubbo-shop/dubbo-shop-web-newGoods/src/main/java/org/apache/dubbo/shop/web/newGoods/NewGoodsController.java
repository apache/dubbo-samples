package org.apache.dubbo.shop.web.newGoods;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.shop.common.ReturnResult;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGoodsList;
import org.apache.dubbo.shop.service.NewGoodsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/good")
@CrossOrigin(originPatterns = "*",allowCredentials = "true")
public class NewGoodsController {
    @DubboReference
    NewGoodsService newGoodsService;

    @GetMapping("/new")
    public ReturnResult newGoodsList(){
        NewGoodsList newGoodsList = newGoodsService.newGoodsListResult();
        return ReturnResult.success(newGoodsList);
    }
}
