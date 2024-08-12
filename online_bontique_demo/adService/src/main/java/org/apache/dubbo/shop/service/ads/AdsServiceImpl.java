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

package org.apache.dubbo.shop.service.ads;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.dto.request.AdRequest;
import org.apache.dubbo.shop.common.dto.response.AdResponse;
import org.apache.dubbo.shop.common.pojo.Ad;
import org.apache.dubbo.shop.service.AdsService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@DubboService
@Service
public class AdsServiceImpl implements AdsService {
    
    private static final int MAX_ADS_TO_SERVE = 2;
    private static final Map<String, List<Ad>> adsMap = createAdsMap();
    
    @Override
    public AdResponse getAds(AdRequest request) {
        List<Ad> allAds = new ArrayList<>();
        AdResponse response = new AdResponse();
        
        if (request.getContextKeys() != null && !request.getContextKeys().isEmpty()) {
            for (String category : request.getContextKeys()) {
                List<Ad> ads = getAdsByCategory(category);
                allAds.addAll(ads);
            }
            if (allAds.isEmpty()) {
                allAds = getRandomAds();
            }
        } else {
            allAds = getRandomAds();
        }
        
        response.setAds(allAds);
        return response;
    }
    
    private List<Ad> getAdsByCategory(String category) {
        return adsMap.getOrDefault(category, new ArrayList<>());
    }
    
    private List<Ad> getRandomAds() {
        List<Ad> ads = new ArrayList<>(MAX_ADS_TO_SERVE);
        List<Ad> allAds = new ArrayList<>();
        
        for (List<Ad> adsList : adsMap.values()) {
            allAds.addAll(adsList);
        }
        
        Random random = new Random();
        for (int i = 0; i < MAX_ADS_TO_SERVE; i++) {
            ads.add(allAds.get(random.nextInt(allAds.size())));
        }
        return ads;
    }
    
    private static Map<String, List<Ad>> createAdsMap() {
        Map<String, List<Ad>> map = new HashMap<>();
        map.put("clothing", List.of(new Ad("/product/66VCHSJNUP", "Tank top for sale. 20% off.")));
        map.put("accessories", List.of(new Ad("/product/1YMWWN1N4O", "Watch for sale. Buy one, get second kit for free")));
        map.put("footwear", List.of(new Ad("/product/L9ECAV7KIM", "Loafers for sale. Buy one, get second one for free")));
        map.put("hair", List.of(new Ad("/product/2ZYFJ3GM2N", "Hairdryer for sale. 50% off.")));
        map.put("decor", List.of(new Ad("/product/0PUK6V6EV0", "Candle holder for sale. 30% off.")));
        map.put("kitchen", List.of(
                new Ad("/product/9SIQT8TOJO", "Bamboo glass jar for sale. 10% off."),
                new Ad("/product/6E92ZMYYFZ", "Mug for sale. Buy two, get third one for free")));
        return map;
    }
}
