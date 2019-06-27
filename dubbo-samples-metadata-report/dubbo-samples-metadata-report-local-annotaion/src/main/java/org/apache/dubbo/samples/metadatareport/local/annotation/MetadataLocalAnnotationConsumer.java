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

package org.apache.dubbo.samples.metadatareport.local.annotation;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.MetadataReportConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.metadatareport.local.annotation.action.AnnotationAction;
import org.apache.dubbo.samples.metadatareport.local.annotation.api.AnnotationService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class MetadataLocalAnnotationConsumer {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        printServiceData();

        AnnotationAction annotationAction = context.getBean("annotationAction", AnnotationAction.class);
        String hello = annotationAction.doSayHello("world");
        System.out.println("result :" + hello);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.metadatareport.local.annotation.action")
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"org.apache.dubbo.samples.metadatareport.local.annotation.action"})
    static public class ConsumerConfiguration {
        @Value("zookeeper://${zookeeper.address:127.0.0.1}:2181")
        private String zookeeperAddress;

        @Bean
        public MetadataReportConfig metadataReportConfig() {
            MetadataReportConfig metadataReportConfig = new MetadataReportConfig();
            metadataReportConfig.setAddress(zookeeperAddress);
            return metadataReportConfig;
        }
    }

    private static void printServiceData() throws Exception {
        Thread.sleep(3000);
        System.out.println("*********************************************************");
        System.out.println("service metadata:");
        System.out.println(ZkUtil.getMetadata("/dubbo", AnnotationService.class.getName(), "1.1.8", "d-test",
                CommonConstants.CONSUMER_SIDE, "metadatareport-local-annotation-consumer"));
        System.out.println("*********************************************************");
    }
}
