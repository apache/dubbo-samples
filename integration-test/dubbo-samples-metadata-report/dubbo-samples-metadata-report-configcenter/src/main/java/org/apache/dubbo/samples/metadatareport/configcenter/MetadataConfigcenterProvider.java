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

package org.apache.dubbo.samples.metadatareport.configcenter;


import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.metadatareport.configcenter.api.AnnotationService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.CountDownLatch;

import static org.apache.dubbo.samples.metadatareport.configcenter.ZKTools.VERSION300;

public class MetadataConfigcenterProvider {

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();

        try {
            ZKTools.generateDubboProperties();
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
            context.start();

            printServiceData();

            System.out.println("dubbo service started");
            new CountDownLatch(1).await();
        } finally {
            ZKTools.removeDubboProperties();
        }
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.metadatareport.configcenter.impl")
    @PropertySource("classpath:/spring/dubbo-provider.properties")
    static public class ProviderConfiguration {
        @Bean
        public ProviderConfig providerConfig() {
            ProviderConfig providerConfig = new ProviderConfig();
            // compatible with dubbo 2.7.x
            if (Version.getIntVersion(Version.getVersion()) < VERSION300) {
                // avoid using spring bean id 'providerConfig' as dubbo config id.
                providerConfig.setId("my-provider");
            } else {
                //TODO remove the following line after merging dubbo 3.0 config refactor pr
                providerConfig.setId("my-provider");
            }
            providerConfig.setTimeout(1000);
            return providerConfig;
        }
    }

    private static void printServiceData() throws Exception {
        Thread.sleep(3000);
        System.out.println("*********************************************************");
        System.out.println("service metadata:");
        System.out.println(ZKTools.getMetadata("/dubbo", AnnotationService.class.getName(), "1.1.1", "d-test",
                CommonConstants.PROVIDER_SIDE, "metadatareport-configcenter-provider"));
        System.out.println("*********************************************************");
    }
}
