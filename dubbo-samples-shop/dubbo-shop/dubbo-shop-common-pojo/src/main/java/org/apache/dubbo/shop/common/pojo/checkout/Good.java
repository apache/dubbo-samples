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
package org.apache.dubbo.shop.common.pojo.checkout;

import lombok.Data;

@Data
public class Good {
    /**
     * 商品属性
     */
    private String attrsText;
    /**
     * 商品数量，购买数量
     */
    private long count;
    /**
     * 商品id
     */
    private String id;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 商品实付单价
     */
    private String payPrice;
    /**
     * 商品图片
     */
    private String picture;
    /**
     * 商品价格
     */
    private String price;
    /**
     * 商品sku的id
     */
    private String skuId;
    /**
     * 商品实付价格小计
     */
    private String totalPayPrice;
    /**
     * 商品小计总价
     */
    private String totalPrice;
}
