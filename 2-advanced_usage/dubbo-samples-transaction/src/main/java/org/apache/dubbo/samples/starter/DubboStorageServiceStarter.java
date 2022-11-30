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
 * The type Dubbo storage service starter.
 */
public class DubboStorageServiceStarter {
    /**
     * 1. Storage service is ready . A seller add 100 storage to a sku: C00321
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext storageContext = new ClassPathXmlApplicationContext("spring/dubbo-storage-service.xml");
        storageContext.getBean("service");
        JdbcTemplate storageJdbcTemplate = (JdbcTemplate) storageContext.getBean("jdbcTemplate");
        storageJdbcTemplate.update("delete from storage_tbl where commodity_code = 'C00321'");
        storageJdbcTemplate.update("insert into storage_tbl(commodity_code, count) values ('C00321', 100)");

        System.out.println("storage service started");
        new CountDownLatch(1).await();
    }
}
