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

package org.apache.dubbo.samples.externalconfiguration.provider;


import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.spring.ConfigCenterBean;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.externalconfiguration.service")
public class AnnotationProvider {

    public static void main(String[] args) throws Exception {
        // start embedded zookeeper server
        new EmbeddedZooKeeper(2181, false).start();

        SpringApplication.run(AnnotationProvider.class, args);
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }

    @Configuration
    static public class ProviderConfiguration {

        /**
         * It's still required to initialize ConfigCenterBean, here we use the JavaBean method, but it doesn't matter
         * which way you use, which means xml or .properties are all ok to go.
         */
        @Bean
        public ConfigCenterBean configCenterBean() {
            ConfigCenterBean configCenterBean = new ConfigCenterBean();
            // This is a critical switch to tell Dubbo framework to get configs from standard Spring Environment
            configCenterBean.setIncludeSpringEnv(true);
            // by default is dubbo.properties
            configCenterBean.setConfigFile("dubbo.properties");
            // by default is application.dubbo.properties
            configCenterBean.setAppConfigFile("configcenter-annotation-provider.dubbo.properties");
            return configCenterBean;
        }

        /**
         * It's ok to have local configuration for each part, there's nothing different with 2.6.x regarding this part.
         * It's only a matter of priority, by default, the external configuration (loaded from standard Spring
         * Environment in this sample) has a higher priority than the local configuration.
         */
        @Bean
        public ProviderConfig providerConfig() {
            ProviderConfig providerConfig = new ProviderConfig();
            providerConfig.setTimeout(6666);
            return providerConfig;
        }
    }
}
