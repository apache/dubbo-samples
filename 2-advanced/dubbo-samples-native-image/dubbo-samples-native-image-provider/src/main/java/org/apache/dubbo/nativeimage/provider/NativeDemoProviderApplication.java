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

package org.apache.dubbo.nativeimage.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages = {"org.apache.dubbo.nativeimage.provider"})
@EnableDubbo(scanBasePackages = {"org.apache.dubbo.nativeimage.provider"})
public class NativeDemoProviderApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(NativeDemoProviderApplication.class, args);
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("dubbo provider application started, The time taken to start the application is "
                + (System.currentTimeMillis() - runtimeMXBean.getStartTime()) +" ms");
        new CountDownLatch(1).await();
    }
}
