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

package org.apache.dubbo.shop.service.recommendation;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.dto.request.ListRecommendationsRequest;
import org.apache.dubbo.shop.common.dto.response.ListRecommendationsResponse;
import org.apache.dubbo.shop.common.pojo.Empty;
import org.apache.dubbo.shop.common.pojo.Product;
import org.apache.dubbo.shop.service.ProductCatalogService;
import org.apache.dubbo.shop.service.RecommendationService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@DubboService
@Service
public class RecommendationServiceImpl implements RecommendationService {
    
    @DubboReference
    private ProductCatalogService productCatalogService;
    @Override
    public ListRecommendationsResponse listRecommendations(ListRecommendationsRequest request) {
        // 获取所有产品
        List<Product> products = productCatalogService.listProducts(new Empty()).getProducts();
        
        // 过滤出未在请求产品ID列表中的产品
        List<String> filteredProductIds = products.stream()
                .map(Product::getId)
                .filter(id -> !request.getProductIds().contains(id))
                .collect(Collectors.toList());
        
        // 从过滤后的ID列表中随机选择4个
        List<String> recommendedProductIds = sample(filteredProductIds, 4);
        
        // 返回推荐结果
        return new ListRecommendationsResponse(recommendedProductIds);
    }
    
    private List<String> sample(List<String> source, Integer count) {
        if (source.size() <= count) {
            return source;
        }
        List<String> result = new ArrayList<>();
        while (result.size() < count) {
            int index = (int) (Math.random() * source.size());
            result.add(source.remove(index));
        }
        return result;
    }
}
