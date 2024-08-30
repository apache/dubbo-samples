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

package org.apache.dubbo.shop.service.productcatalogs;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.dto.request.GetProductRequest;
import org.apache.dubbo.shop.common.dto.response.ListProductsResponse;
import org.apache.dubbo.shop.common.pojo.Empty;
import org.apache.dubbo.shop.common.pojo.Product;
import org.apache.dubbo.shop.service.ProductCatalogService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DubboService
@Service
public class ProductCatalogServiceImpl implements ProductCatalogService, Serializable {

    private List<Product> products = new ArrayList<>();

    // Load products from JSON file
    {
        loadProductsFromJson();
    }

    @Override
    public ListProductsResponse listProducts(Empty request) {
        return new ListProductsResponse(products);
    }

    @Override
    public Product getProduct(GetProductRequest request) {
        return products.stream()
                .filter(product -> product.getId().equals(request.getId()))
                .findFirst()
                .orElse(null);
    }

    private void loadProductsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource("products.json");
            ProductsWrapper productsWrapper = objectMapper.readValue(resource.getInputStream(), ProductsWrapper.class);
            this.products = productsWrapper.getProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 内部类用于解析JSON
    @Data
    public static class ProductsWrapper {
        private List<Product> products;
    }
}
