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

package org.apache.dubbo.shop.shipping;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.dto.request.GetQuoteRequest;
import org.apache.dubbo.shop.common.dto.request.ShipOrderRequest;
import org.apache.dubbo.shop.common.dto.response.GetQuoteResponse;
import org.apache.dubbo.shop.common.dto.response.ShipOrderResponse;
import org.apache.dubbo.shop.common.pojo.Money;
import org.apache.dubbo.shop.service.ShippingService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@DubboService
@Service
@Slf4j
public class ShippingServiceImpl implements ShippingService {
    
    @Override
    public GetQuoteResponse getQuote(GetQuoteRequest request) {
        log.info("[GetQuote] received request");
        
        Money costUsd = new Money();
        costUsd.setCurrencyCode("USD");
        costUsd.setNanos(99 * 1_000_000);
        costUsd.setUnits(8L);
        
        GetQuoteResponse response = new GetQuoteResponse();
        response.setCostUsd(costUsd);
        
        log.info("[GetQuote] completed request");
        return response;
    }
    
    @Override
    public ShipOrderResponse shipOrder(ShipOrderRequest request) {
        log.info("[ShipOrder] received request");
        
        String baseAddress = String.format("%s, %s, %s", request.getAddress().getStreetAddress(), request.getAddress().getCity(), request.getAddress().getState());
        String trackingId = createTrackingId(baseAddress);
        
        ShipOrderResponse response = new ShipOrderResponse();
        response.setTrackingId(trackingId);
        
        log.info("[ShipOrder] completed request");
        return response;
    }
    
    private String createTrackingId(String salt) {
        Random random = new Random();
        return String.format("%c%c-%d%s-%d%s",
                getRandomLetterCode(random),
                getRandomLetterCode(random),
                salt.length(),
                getRandomNumber(random, 3),
                salt.length() / 2,
                getRandomNumber(random, 7));
    }
    
    private char getRandomLetterCode(Random random) {
        return (char) (65 + random.nextInt(26));
    }
    
    private String getRandomNumber(Random random, int digits) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
