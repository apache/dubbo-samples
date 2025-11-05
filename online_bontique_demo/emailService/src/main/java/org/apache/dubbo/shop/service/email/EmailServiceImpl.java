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

package org.apache.dubbo.shop.service.email;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.shop.service.EmailService;

import org.springframework.stereotype.Service;

@DubboService
@Service
public class EmailServiceImpl implements EmailService {
    
    @Override
    public String sendOrderConfirmation(String email, String order) {
        // 模拟发送订单确认邮件的逻辑
        System.out.printf("Sending order confirmation to %s for order %s%n", email, order);
        return "Order confirmation sent successfully!";
    }
}
