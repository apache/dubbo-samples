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
package oeg.apache.dubbo.shop.web.goodsDetails;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.shop.common.ReturnResult;
import org.apache.dubbo.shop.common.pojo.GoodsDetails.Details;
import org.apache.dubbo.shop.service.GoodsDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Component
@RestController
@RequestMapping("/detail")
@CrossOrigin(originPatterns = "*",allowCredentials = "true")
public class GoodsDetailsController {
    @DubboReference
    GoodsDetails goodsDetails;
    @GetMapping("/id")
    public ReturnResult GoodsDetailsList(@RequestParam("id") Integer id){
        Details details = goodsDetails.GoodsResult(id);
        return ReturnResult.success(details);
    }
}
