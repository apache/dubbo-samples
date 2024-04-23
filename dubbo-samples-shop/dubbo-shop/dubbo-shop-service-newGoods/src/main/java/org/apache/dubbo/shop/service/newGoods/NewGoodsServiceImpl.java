package org.apache.dubbo.shop.service.newGoods;
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
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGood;
import org.apache.dubbo.shop.common.pojo.NewGoodsList.NewGoodsList;
import org.apache.dubbo.shop.mapper.NewGoods.NewGoodsMapper;
import org.apache.dubbo.shop.service.NewGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class NewGoodsServiceImpl implements NewGoodsService {
    @Autowired
    NewGoodsMapper newGoodsMapper;
    @Override
    public NewGoodsList newGoodsListResult() {
        NewGood[] newGoods = new NewGood[4];
        for (int i = 1; i <= 4; i++){
            NewGood newGood = newGoodsMapper.getNewGoods(i);
            newGoods[i-1] = newGood;
        }

        NewGoodsList newGoodsList = new NewGoodsList();
        newGoodsList.setNewGoods(newGoods);
        System.out.println(newGoodsList);
        return newGoodsList;
    }
}
