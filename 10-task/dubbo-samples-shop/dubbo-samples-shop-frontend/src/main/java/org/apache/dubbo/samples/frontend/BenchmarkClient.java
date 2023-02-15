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
package org.apache.dubbo.samples.frontend;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.ShopService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class BenchmarkClient implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(BenchmarkClient.class);

    @DubboReference
    private ShopService shopService;

    @Override
    public void run(String... args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // Register (5 Concurrent)
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                while (true) {
                    try {
                        List<String> names = Arrays.asList("John", "Mike", "Kevin", "Grace", "Mark");
                        String name = names.get(ThreadLocalRandom.current().nextInt(names.size()));

                        List<String> passwords = Arrays.asList("123456", "654321", "admin", "root", "pwd");
                        String password = passwords.get(ThreadLocalRandom.current().nextInt(passwords.size()));
                        shopService.register(name, password, name, "dev@dubbo.apache.org", "12345678");
                    } catch (Throwable t) {
                        logger.error("Running Register Bench Failed.", t);
                    }
                }
            });
        }
        logger.info("Start Register Bench with 5 Concurrent.");

        // Login (10 Concurrent)
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                while (true) {
                    try {
                        List<String> names = Arrays.asList("John", "Mike", "Kevin", "Grace", "Mark");
                        String name = names.get(ThreadLocalRandom.current().nextInt(names.size()));

                        List<String> passwords = Arrays.asList("123456", "654321", "admin", "root", "pwd");
                        String password = passwords.get(ThreadLocalRandom.current().nextInt(passwords.size()));
                        shopService.login(name, password);
                    } catch (Throwable t) {
                        logger.error("Running Login Bench Failed.", t);
                    }
                }
            });
        }
        logger.info("Start Login Bench with 5 Concurrent.");

        // CreateItem (5 Concurrent)
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                while (true) {
                    try {
                        long sku = ThreadLocalRandom.current().nextLong();
                        shopService.checkItem(sku, "dubbo");
                    } catch (Throwable t) {
                        logger.error("Running CreateItem Bench Failed.", t);
                    }
                }
            });
        }
        logger.info("Start CreateItem Bench with 5 Concurrent.");

        // SubmitOrder (50 Concurrent)
        for (int i = 0; i < 50; i++) {
            executorService.submit(() -> {
                while (true) {
                    try {
                        long sku = ThreadLocalRandom.current().nextLong();
                        shopService.submitOrder(sku, 50, "Test Item", "Desc", "Mike");
                    } catch (Throwable t) {
                        logger.error("Running SubmitOrder Bench Failed.", t);
                    }
                }
            });
        }
        logger.info("Start SubmitOrder Bench with 5 Concurrent.");

        logger.info("All Bench Client started.");
    }
}
