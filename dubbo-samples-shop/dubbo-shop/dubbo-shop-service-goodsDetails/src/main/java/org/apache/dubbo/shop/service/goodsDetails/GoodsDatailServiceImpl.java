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
package org.apache.dubbo.shop.service.goodsDetails;


import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.pojo.GoodsDetails.Details;
import org.apache.dubbo.shop.mapper.GoodsDatails.GoodsDetailsMapper;
import org.apache.dubbo.shop.service.GoodsDetails;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class GoodsDatailServiceImpl implements GoodsDetails {
    @Autowired
    GoodsDetailsMapper goodsDetailsMapper;
    @Override
    public Details GoodsResult(Integer id) {
        Details details = goodsDetailsMapper.getGoodsDetails(id);
        //为了传给前端mainPictures这个名字的参数，进行数据处理
        details.setMainPictures(details.getPicture());
        return  details;
    }
}
