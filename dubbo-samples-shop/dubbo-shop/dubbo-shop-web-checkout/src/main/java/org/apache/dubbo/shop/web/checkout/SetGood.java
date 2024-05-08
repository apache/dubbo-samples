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
package org.apache.dubbo.shop.web.checkout;

import org.apache.dubbo.shop.attributeClass.checkout.Good;
import org.apache.dubbo.shop.attributeClass.checkout.Summary;

public class SetGood {
    public static Good[] setgood(){
        Good[] goods = new Good[1];
        Good good = new Good();
        good.setId("1");
        good.setCount(1);
        good.setName("toukui");
        good.setPicture("/assets/images/liuying.png");
        good.setPrice("190");
        good.setAttrsText("好货");
        good.setSkuId("1");
        good.setTotalPayPrice("190");
        good.setPayPrice("190");
        good.setTotalPrice("190");
        goods[0] = good;
        return goods;
    }
    public static Summary setSummary(){
        Summary summary = new Summary();
        summary.setGoodsCount(1);
        summary.setTotalPrice(190);
        summary.setDiscountPrice(0);
        summary.setPostFee(100);
        return  summary;
    }
}
