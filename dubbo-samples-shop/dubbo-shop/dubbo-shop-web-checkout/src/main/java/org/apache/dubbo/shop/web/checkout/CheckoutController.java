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

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.shop.common.pojo.checkout.CheckoutData;
import org.apache.dubbo.shop.service.CheckoutService;
import org.apache.dubbo.shop.common.ReturnResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*")
public class CheckoutController {
    @DubboReference
    CheckoutService checkoutService;
    @GetMapping("/order")
    public ReturnResult checkout(Integer orderId){
        CheckoutData checkoutData = checkoutService.checkoutResult(orderId);
        return ReturnResult.success(checkoutData);
    }
}
