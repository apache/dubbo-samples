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

package org.apache.dubbo.shop.currency;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.dto.request.CurrencyConversionRequest;
import org.apache.dubbo.shop.common.dto.response.GetSupportedCurrenciesResponse;
import org.apache.dubbo.shop.common.pojo.Empty;
import org.apache.dubbo.shop.common.pojo.Money;
import org.apache.dubbo.shop.service.CurrencyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@DubboService
@Service
public class CurrencyServiceImpl implements CurrencyService {
    
    private Map<String, Double> loadCurrencyData() {
        try {
            ClassPathResource resource = new ClassPathResource("currency_conversion.json");
            byte[] jsonData = resource.getInputStream().readAllBytes();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonData, Map.class);
        } catch (IOException e) {
            log.error("Failed to load currency data", e);
            throw new RuntimeException("Failed to load currency data", e);
        }
    }
    @Override
    public GetSupportedCurrenciesResponse getSupportedCurrencies(Empty request) {
        Map<String, Double> currencies = loadCurrencyData();
        GetSupportedCurrenciesResponse response = new GetSupportedCurrenciesResponse();
        response.setCurrencyCodes(currencies.keySet().stream().toList());
        return response;
    }
    
    @Override
    public Money convert(CurrencyConversionRequest request) {
        Map<String, Double> currencies = loadCurrencyData();
        
        Double fromCurrencyRate = currencies.get(request.getFrom().getCurrencyCode());
        Double toCurrencyRate = currencies.get(request.getToCode());
        if (fromCurrencyRate == null || toCurrencyRate == null) {
            throw new RuntimeException("Unsupported currency code");
        }
        Long totalNanos = request.getFrom().getUnits() * 1_000_000_000 + request.getFrom().getNanos();
        double convertedNanos = totalNanos * toCurrencyRate / fromCurrencyRate;
        
        Money response = new Money();
        response.setCurrencyCode(request.getToCode());
        response.setUnits((long) (convertedNanos / 1_000_000_000L));
        response.setNanos((int) (convertedNanos % 1_000_000_000L));
        return response;
    }
}
