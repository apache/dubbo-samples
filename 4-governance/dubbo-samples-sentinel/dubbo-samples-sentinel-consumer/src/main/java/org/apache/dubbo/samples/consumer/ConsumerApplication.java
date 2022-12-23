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
package org.apache.dubbo.samples.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.sentinel.DemoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Component
    static class DemoTask implements CommandLineRunner {
        private static final Logger logger = LoggerFactory.getLogger(DemoTask.class);

        @DubboReference
        private DemoService demoService;

        @Override
        public void run(String... args) {
            Executors.newScheduledThreadPool(1)
                    .scheduleAtFixedRate(() -> {
                        logger.info("Start to call remote.");
                        for (int i = 0; i < 15; i++) {
                            try {
                                String result = demoService.sayHello("dubbo");
                                logger.info("Call Count:" + i + " Dubbo Remote Return ======> " + result);
                            } catch (RuntimeException ex) {
                                if (ex.getMessage().contains("SentinelBlockException: FlowException")) {
                                    logger.info("Call Count:" + i + " Blocked");
                                } else {
                                    logger.error("Call Count:" + i + " Request Failed.", ex);
                                }
                            }
                        }
                    }, 0, 5000, TimeUnit.MILLISECONDS);
        }
    }
}
