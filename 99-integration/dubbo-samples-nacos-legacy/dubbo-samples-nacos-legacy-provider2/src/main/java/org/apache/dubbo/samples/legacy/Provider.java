/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.dubbo.samples.legacy;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.samples.legacy.api.DemoService;
import org.apache.dubbo.samples.legacy.impl.DemoServiceImpl;

import com.alibaba.dubbo.config.RegistryConfig;

import java.util.concurrent.CountDownLatch;

public class Provider {
    public static void main(String[] args) throws InterruptedException {
        ServiceConfig<DemoService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(DemoService.class);
        serviceConfig.setRef(new DemoServiceImpl());
        serviceConfig.setProtocol(new ProtocolConfig("dubbo", 20881));
        serviceConfig.setApplication(new ApplicationConfig("demo"));
        serviceConfig.setRegistry(new RegistryConfig("nacos://" + System.getProperty("nacos.address", "127.0.0.1") + ":8848?username=nacos&password=nacos"));
        serviceConfig.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
