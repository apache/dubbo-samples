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
package org.apache.dubbo.samples.seata;

import org.apache.dubbo.samples.service.BusinessService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerIT {

    private BusinessService businessService;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void testRollback() {
        restoreDatabase();
        try {
            businessService.purchase("U100001", "C00321", 2, true);
            Assert.fail();
        } catch (RuntimeException t) {
            if (!t.getMessage().contains("xxx")) {
                Assert.fail(t.getMessage());
            }
        }
        restoreDatabase();
    }

    @Test
    public void testCommit() {
        restoreDatabase();
        try {
            businessService.purchase("U100001", "C00321", 2, false);
        } catch (RuntimeException t) {
            Assert.fail(t.getMessage());
        }
        restoreDatabase();
    }

    public void restoreDatabase() {
        jdbcTemplate.update("delete from account_tbl where user_id = 'U100001'");
        jdbcTemplate.update("insert into account_tbl(user_id, money) values ('U100001', 999)");
        jdbcTemplate.update("delete from storage_tbl where commodity_code = 'C00321'");
        jdbcTemplate.update("insert into storage_tbl(commodity_code, count) values ('C00321', 100)");
        jdbcTemplate.update("delete from order_tbl");
    }

}
