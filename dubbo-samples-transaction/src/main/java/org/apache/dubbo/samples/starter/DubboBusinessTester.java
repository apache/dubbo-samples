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

package org.apache.dubbo.samples.starter;

import org.apache.dubbo.samples.service.BusinessService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The type Dubbo business tester.
 */
public class DubboBusinessTester {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        /**
         *  4. The whole e-commerce platform is ready , The buyer(U100001) create an order on the sku(C00321) , the count is 2
         */
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-business.xml");
        final BusinessService business = (BusinessService) context.getBean("business");
        business.purchase("U100001", "C00321", 2);
    }
}
