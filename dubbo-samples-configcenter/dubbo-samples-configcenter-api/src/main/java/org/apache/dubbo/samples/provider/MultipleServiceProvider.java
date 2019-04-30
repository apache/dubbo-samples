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

package org.apache.dubbo.samples.provider;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.samples.api.DemoService;
import org.apache.dubbo.samples.api.GreetingsService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MultipleServiceProvider {
    private static ConfigCenterConfig configCenter = new ConfigCenterConfig();
    private static ApplicationConfig application = new ApplicationConfig("api-dubbo-provider");
    private static RegistryConfig registry1 = new RegistryConfig();
    private static RegistryConfig registry2 = new RegistryConfig();

    static {
        configCenter.setExternalConfig(getExternalConfiguration());
        registry1.setAddress("zookeeper://127.0.0.1:2181");
        registry2.setAddress("zookeeper://127.0.0.1:2181");
    }

    public static void main(String[] args) throws Exception {
        ServiceConfig<GreetingsService> greetingsService = new ServiceConfig<>();
        greetingsService.setApplication(application);
        greetingsService.setConfigCenter(configCenter);
        greetingsService.setRegistries(Arrays.asList(registry1, registry2));
        greetingsService.setInterface(GreetingsService.class);
        greetingsService.setRef(new GreetingsServiceImpl());
        greetingsService.export();

        ServiceConfig<DemoService> demoService = new ServiceConfig<>();
        demoService.setApplication(application);
        demoService.setConfigCenter(configCenter);
        demoService.setInterface(DemoService.class);
        demoService.setRegistries(Arrays.asList(registry1, registry2));
        demoService.setRef(new DemoServiceImpl());
        demoService.export();

        System.out.println("Dubbo provider started successfully!");
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
        // you will need to add the configcenter address if you want to use the service governance features in 2.7, e.g., overrides and routers.
        // but notice it will not be used for gathering startup configurations.
        dubboConfigurations.put("dubbo.configcenter.address", "zookeeper://127.0.0.1:2181");

        return dubboConfigurations;
    }
}
