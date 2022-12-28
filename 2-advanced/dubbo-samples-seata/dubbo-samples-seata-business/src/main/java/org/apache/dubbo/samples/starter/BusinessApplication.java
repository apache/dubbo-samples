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

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.service.BusinessService;
import org.apache.dubbo.samples.service.StorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Component
    static class Tester implements CommandLineRunner {
        private static final Logger LOGGER = LoggerFactory.getLogger(Tester.class);

        private BusinessService businessService;

        private JdbcTemplate jdbcTemplate;

        @DubboReference
        private StorageService storageService;

        @Autowired
        public void setBusinessService(BusinessService businessService) {
            this.businessService = businessService;
        }

        @Autowired
        public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public void testRollback() {
            LOGGER.info("======= Start to test purchase with rollback.");
            LOGGER.info("Commodity count before purchase: {}", storageService.queryCount("C00321"));
            try {
                businessService.purchase("U100001", "C00321", 2, true);
            } catch (RuntimeException t) {
                if (t.getMessage().contains("xxx")) {
                    LOGGER.info("Purchase rollback triggered.");
                } else {
                    LOGGER.error("Purchase failed with unknown exception.", t);
                }
            }
            LOGGER.info("Commodity count after purchase: {}", storageService.queryCount("C00321"));
            LOGGER.info("======= Test purchase with rollback end.");
            restoreDatabase();
        }

        public void testCommit() {
            LOGGER.info("======= Start to test purchase.");
            LOGGER.info("Commodity count before purchase: {}", storageService.queryCount("C00321"));
            try {
                businessService.purchase("U100001", "C00321", 2, false);
                LOGGER.info("Purchase success.");
            } catch (RuntimeException t) {
                LOGGER.error("Purchase failed with unknown exception.", t);
            }
            LOGGER.info("Commodity count after purchase: {}", storageService.queryCount("C00321"));
            LOGGER.info("======= Test purchase end.");
            restoreDatabase();
        }

        public void restoreDatabase() {
            LOGGER.info("Restoring Database.");
            jdbcTemplate.update("delete from account_tbl where user_id = 'U100001'");
            jdbcTemplate.update("insert into account_tbl(user_id, money) values ('U100001', 999)");
            jdbcTemplate.update("delete from storage_tbl where commodity_code = 'C00321'");
            jdbcTemplate.update("insert into storage_tbl(commodity_code, count) values ('C00321', 100)");
            jdbcTemplate.update("delete from order_tbl");
        }

        @Override
        public void run(String... args) throws Exception {
            Executors.newScheduledThreadPool(1)
                    .scheduleAtFixedRate(() -> {
                        try {
                            testCommit();
                            testRollback();
                        } catch (Exception e) {
                            LOGGER.error("Error occurred when testing.", e);
                        }
                    }, 0, 3000, TimeUnit.MILLISECONDS);
        }
    }

}
