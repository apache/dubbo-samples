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


import org.apache.dubbo.config.spring.ConfigCenterBean;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MergeProvider
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.externalconfiguration.service")
public class AnnotationProvider {

    public static void main(String[] args) throws Exception {
        // start embedded zookeeper server
        new EmbeddedZooKeeper(2181, false).start();

        SpringApplication.run(AnnotationProvider.class, args);
        System.in.read();
    }

    @Configuration
    static public class ProviderConfiguration {
        @Bean
        public ConfigCenterBean configCenterBean() {
            ConfigCenterBean configCenterBean = new ConfigCenterBean();
            configCenterBean.setAuto(true);
            return configCenterBean;
        }

    }

}
