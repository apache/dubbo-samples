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

package org.apache.dubbo.samples.simplified.annotation;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.simplified.annotation.action.AnnotationAction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static org.apache.dubbo.common.constants.CommonConstants.RELEASE_KEY;

public class SimpleRegistryAnnotationConsumer {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        AnnotationAction annotationAction = context.getBean("annotationAction", AnnotationAction.class);
        printServiceData();

        String hello = annotationAction.doSayHello("world");
        System.out.println("result :" + hello);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.simplified.annotation.action")
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"org.apache.dubbo.samples.simplified.annotation.action"})
    static public class ConsumerConfiguration {
        @Value("zookeeper://${zookeeper.address:127.0.0.1}:${zookeeper.port:2181}")
        private String zookeeperAddress;

        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress(zookeeperAddress);
            registryConfig.setSimplified(true);
            return registryConfig;
        }
    }

    private static void printServiceData() {
        List<String> urls = ZkUtil.getChildren(ZkUtil.toUrlPath("consumers"));
        System.out.println("*********************************************************");
        urls.stream().map(URL::decode).forEach(System.out::println);
        System.out.println("not contains 'retries':" + !urls.get(0).contains("retries"));
        System.out.println("not contains 'owner':" + !urls.get(0).contains("owner"));
        System.out.println("not contains 'actives':" + !urls.get(0).contains("actives"));
        System.out.println("not contains 'timeout':" + !urls.get(0).contains("timeout"));
        System.out.println("contains 'application':" + urls.get(0).contains("application"));
        System.out.println("contains 'version':" + urls.get(0).contains("version"));
        System.out.println("contains 'group':" + urls.get(0).contains("group"));
        System.out.println("contains 'specVersion(default)':" + urls.get(0).contains(RELEASE_KEY));
        System.out.println("*********************************************************");
    }
}
