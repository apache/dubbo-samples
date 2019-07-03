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

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.CountDownLatch;

/**
 * The type Dubbo account service starter.
 */
public class DubboAccountServiceStarter {
    /**
     * 2. Account service is ready. A buyer register an account: U100001 on my e-commerce platform
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext accountContext = new ClassPathXmlApplicationContext("spring/dubbo-account-service.xml");
        accountContext.getBean("service");
        JdbcTemplate accountJdbcTemplate = (JdbcTemplate) accountContext.getBean("jdbcTemplate");
        accountJdbcTemplate.update("delete from account_tbl where user_id = 'U100001'");
        accountJdbcTemplate.update("insert into account_tbl(user_id, money) values ('U100001', 999)");

        System.out.println("account service started");
        new CountDownLatch(1).await();
    }
}
