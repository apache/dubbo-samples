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
package org.apache.dubbo.samples.a;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.ServiceA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component
public class BenchmarkClient implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(BenchmarkClient.class);

    @DubboReference
    private ServiceA serviceA;

    @Override
    public void run(String... args) {
        Runnable task = () -> {
            long startTime = System.currentTimeMillis();
            List<String> names = Arrays.asList("John", "Mike", "Kevin", "Grace", "Mark");
            String name = names.get(ThreadLocalRandom.current().nextInt(names.size()));
            String result = serviceA.sayHello(name) + "Usage Time: " + (System.currentTimeMillis() - startTime) + "ms";
            logger.info(result);
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executorService.scheduleAtFixedRate(task, ThreadLocalRandom.current().nextInt(1000), 1000, TimeUnit.MILLISECONDS);
        }
    }
}
