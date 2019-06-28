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

package org.apache.dubbo.samples.server;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.samples.api.GreetingsService;

import java.util.HashMap;
import java.util.Map;

public class Provider1 {

    private static ConfigCenterConfig configCenter = new ConfigCenterConfig();
    private static ApplicationConfig applicationConfig = new ApplicationConfig("api-dubbo-provider-1");

    static {
        configCenter.setExternalConfig(getExternalConfiguration());
    }

    public static void main(String[] args) throws Exception {
        ServiceConfig<GreetingsService> service = new ServiceConfig<>();
        service.setApplication(applicationConfig);
        service.setConfigCenter(configCenter);
        service.setInterface(GreetingsService.class);
        service.setRef(new GreetingsServiceImpl());
        service.export();
        System.out.println("dubbo service started");
        System.in.read();
    }

    /**
     * If you don't want to use ConfigCenter provided by dubbo, you can set external configuration to Dubbo directly.
     * In this sample, we created a Map instance manually and put a value into it, but in reality,
     * the external configurations will most likely being generated from other plugins in your system.
     */
    public static Map<String, String> getExternalConfiguration() {
        Map<String, String> dubboConfigurations = new HashMap<>();
        dubboConfigurations.put("dubbo.registry.address", "zookeeper://127.0.0.1:2181");
        // you will need to add the configcenter address if you want to use the service governance features in 2.7,
        // e.g., overrides and routers, but notice it will not be used for gathering startup configurations.
        dubboConfigurations.put("dubbo.config-center.address", "zookeeper://127.0.0.1:2181");

        return dubboConfigurations;
    }
}
