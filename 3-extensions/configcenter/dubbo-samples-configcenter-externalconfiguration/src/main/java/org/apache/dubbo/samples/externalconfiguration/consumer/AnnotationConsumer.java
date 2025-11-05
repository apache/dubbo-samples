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

package org.apache.dubbo.samples.externalconfiguration.consumer;

import org.apache.dubbo.config.spring.ConfigCenterBean;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.externalconfiguration.consumer")
public class AnnotationConsumer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AnnotationConsumer.class, args);
        AnnotationAction action = context.getBean(AnnotationAction.class);

        String result = action.doSayHello("world");
        System.out.println("result: " + result);
    }

    @Configuration
    static public class ConsumerConfiguration {

        /**
         * It's still required to initialize ConfigCenterBean, here we use the JavaBean method, but it doesn't matter
         * which way you use, for example, xml or .properties are all ok.
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
    }
}
