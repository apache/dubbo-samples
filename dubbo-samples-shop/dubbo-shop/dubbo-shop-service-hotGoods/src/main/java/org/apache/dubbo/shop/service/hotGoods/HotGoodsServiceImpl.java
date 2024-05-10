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
package org.apache.dubbo.shop.service.hotGoods;


import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.pojo.HotGoodsList.HotGood;
import org.apache.dubbo.shop.common.pojo.HotGoodsList.HotGoodsList;
import org.apache.dubbo.shop.mapper.HotGoods.HotGoodsMapper;
import org.apache.dubbo.shop.mapper.NewGoods.NewGoodsMapper;
import org.apache.dubbo.shop.service.HotGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
@DubboService
public class HotGoodsServiceImpl implements HotGoodsService {
    @Autowired
    HotGoodsMapper hotGoodsMapper;
    @Override
    public HotGoodsList hotGoodsListResult() {
        HotGood[] hotGoods = new HotGood[4];
        for (int i = 5; i <= 8; i++){
            HotGood hotGood = hotGoodsMapper.getHotGoods(i);
            hotGoods[i-5] = hotGood;
        }

        HotGoodsList hotGoodsList = new HotGoodsList();
        hotGoodsList.setHotGoods(hotGoods);
        System.out.println(hotGoodsList);
        return hotGoodsList;
    }
}
