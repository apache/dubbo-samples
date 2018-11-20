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

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.RegistryDataConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.dubbo.remoting.zookeeper.ZookeeperTransporter;
import org.apache.dubbo.samples.simplified.annotation.action.AnnotationAction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * CallbackConsumer
 */
public class AnnotationConsumer {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        final AnnotationAction annotationAction = (AnnotationAction) context.getBean("annotationAction");
        // get service data(consumer) from zookeeper.
        printServiceData();

        String hello = annotationAction.doSayHello("world");
        System.out.println("result :" + hello);
        System.in.read();
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.simplified.annotation.action")
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"org.apache.dubbo.samples.simplified.annotation.action"})
    static public class ConsumerConfiguration {
        @Bean
        public RegistryDataConfig registryDataConfig() {
            RegistryDataConfig registryDataConfig = new RegistryDataConfig();
            registryDataConfig.setExtraConsumerKeys("actives,owner");
            registryDataConfig.setSimpleConsumerConfig(true);
            return registryDataConfig;
        }
    }

    private static void printServiceData() {
        ZookeeperClient zookeeperClient = ExtensionLoader.getExtensionLoader(ZookeeperTransporter.class).getExtension("curator").connect(new URL("zookeeper", "127.0.0.1", 2181));
        List<String> urls = zookeeperClient.getChildren(ZkUtil.toUrlPath("consumers"));
        System.out.println("*********************************************************");
        System.out.println(urls);
        System.out.println("simple donot contain 'retries':" + !urls.get(0).contains("retries"));
        System.out.println("simple contain 'owner':" + urls.get(0).contains("owner"));
        System.out.println("simple contain 'actives':" + urls.get(0).contains("actives"));
        System.out.println("simple donot contain 'timeout':" + !urls.get(0).contains("timeout"));
        System.out.println("simple contain 'application':" + urls.get(0).contains("application"));
        System.out.println("simple contain 'version':" + urls.get(0).contains("version"));
        System.out.println("simple contain 'group':" + urls.get(0).contains("group"));
        System.out.println("simple contain 'specVersion(default)':" + urls.get(0).contains(Constants.SPECIFICATION_VERSION_KEY));
        System.out.println("*********************************************************");
    }
}
