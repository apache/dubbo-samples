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

package org.apache.dubbo.shop.payment;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.common.dto.request.ChargeRequest;
import org.apache.dubbo.shop.common.dto.response.ChargeResponse;
import org.apache.dubbo.shop.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@DubboService
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    
    @Override
    public ChargeResponse charge(ChargeRequest request) {
        // 验证信用卡信息（可以使用第三方库进行验证）
        // 这里只是简单示例，没有实际验证逻辑
        
        log.info("Transaction processed: {}, Amount: {}{}{}",
                request.getCreditCard().getCreditCardNumber(),
                request.getAmount().getCurrencyCode(),
                request.getAmount().getUnits(),
                request.getAmount().getNanos());
        
        String transactionId = UUID.randomUUID().toString();
        ChargeResponse response = new ChargeResponse();
        response.setTransactionId(transactionId);
        return response;
    }
}
