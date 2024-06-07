/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.shop.web;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.shop.common.ReturnResult;
import org.apache.dubbo.shop.common.pojo.Ads.AdsGoodsList;
import org.apache.dubbo.shop.common.pojo.GoodsDetails.Details;
import org.apache.dubbo.shop.common.pojo.HotGoodsList.HotGoodsList;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGoodsList;
import org.apache.dubbo.shop.common.pojo.checkout.CheckoutData;
import org.apache.dubbo.shop.service.AdsService;
import org.apache.dubbo.shop.service.CheckoutService;
import org.apache.dubbo.shop.service.GoodsDetails;
import org.apache.dubbo.shop.service.HotGoodsService;
import org.apache.dubbo.shop.service.NewGoodsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@CrossOrigin(originPatterns = "*",allowCredentials = "true")
public class WebController {
    @DubboReference
    AdsService adsService;
    @DubboReference
    CheckoutService checkoutService;
    @DubboReference
    GoodsDetails goodsDetails;
    @DubboReference
    HotGoodsService hotGoodsService;
    @DubboReference
    NewGoodsService newGoodsService;
    @GetMapping("/goods/hot")
    public ReturnResult adsGoosList(){
        AdsGoodsList adsGoodsList = adsService.adsGoodsListResult();
        return ReturnResult.success(adsGoodsList);
    }
    @GetMapping("/member/order")
    public ReturnResult checkout(){
        CheckoutData checkoutData = checkoutService.checkoutResult();
        return ReturnResult.success(checkoutData);
    }
    @GetMapping("/detail/id")
    public ReturnResult GoodsDetailsList(@RequestParam("id") Integer id){
        Details details = goodsDetails.GoodsResult(id);
        return ReturnResult.success(details);
    }
    @GetMapping("/good/new")
    public ReturnResult newGoodsList(){
        NewGoodsList newGoodsList = newGoodsService.newGoodsListResult();
        return ReturnResult.success(newGoodsList);
    }
    @GetMapping("/good/hot")
    public ReturnResult hotGoodsList(){
        HotGoodsList hotGoodsList = hotGoodsService.hotGoodsListResult();
        return ReturnResult.success(hotGoodsList);
    }

}
