/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.apache.dubbo.samples.seata.business.service;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.seata.api.BusinessService;
import org.apache.dubbo.samples.seata.api.OrderService;
import org.apache.dubbo.samples.seata.api.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessService.class);

    @DubboReference(check = false)
    private StockService stockService;
    @DubboReference(check = false)
    private OrderService orderService;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "dubbo-samples-seata")
    public void purchaseCommit(String userId, String commodityCode, int orderCount) {
        LOGGER.info("purchase begin ... xid: " + RootContext.getXID());
        stockService.deduct(commodityCode, orderCount);
        orderService.create(userId, commodityCode, orderCount);
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "dubbo-samples-seata")
    public void purchaseRollback(String userId, String commodityCode, int orderCount) {
        LOGGER.info("purchase begin ... xid: " + RootContext.getXID());
        stockService.deduct(commodityCode, orderCount);
        orderService.create(userId, commodityCode, orderCount);
        throw new RuntimeException("purchase rollback!");

    }

}
