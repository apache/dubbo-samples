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
package org.apache.dubbo.samples.order;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.Order;
import org.apache.dubbo.samples.OrderDetail;
import org.apache.dubbo.samples.OrderService;

import java.util.concurrent.ThreadLocalRandom;

@DubboService
public class OrderServiceImplGray implements OrderService {
    @Override
    public OrderDetail submitOrder(Order order) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setEnv("gray");
        orderDetail.setAddress(order.getAddress());
        orderDetail.setReceiver(order.getReceiver());
        orderDetail.setPhone(order.getPhone());
        return orderDetail;
    }
}
