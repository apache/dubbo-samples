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

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.MetadataReportConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.metadata.identifier.ConsumerMetadataIdentifier;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.dubbo.remoting.zookeeper.ZookeeperTransporter;
import org.apache.dubbo.samples.metadatareport.local.annotation.action.AnnotationAction;
import org.apache.dubbo.samples.metadatareport.local.annotation.api.AnnotationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * CallbackConsumer
 */
public class AnnotationConsumer {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        // get service data(consumer) from zookeeper.
        printServiceData();

        final AnnotationAction annotationAction = (AnnotationAction) context.getBean("annotationAction");
        String hello = annotationAction.doSayHello("world");
        System.out.println("result :" + hello);
        System.in.read();
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.metadatareport.local.annotation.action")
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    @ComponentScan(value = {"org.apache.dubbo.samples.metadatareport.local.annotation.action"})
    static public class ConsumerConfiguration {
        @Bean
        public MetadataReportConfig metadataReportConfig() {
            MetadataReportConfig metadataReportConfig = new MetadataReportConfig();
            metadataReportConfig.setAddress("zookeeper://127.0.0.1:2181");
            return metadataReportConfig;
        }
    }

    private static void printServiceData(){
        // get service data(consumer) from zookeeper.
        ZookeeperClient zookeeperClient = ExtensionLoader.getExtensionLoader(ZookeeperTransporter.class).getExtension("curator").connect(new URL("zookeeper", "127.0.0.1", 2181));
        String data = zookeeperClient.getContent(ZkUtil.getNodePath(new ConsumerMetadataIdentifier(AnnotationService.class.getName(), "1.1.8", "d-test", "metadatareport-local-annotaion-consumer")));
        System.out.println("*********************************************************");
        System.out.println("Dubbo store consumer param into special store(as zk,redis)  when local annotation:");
        System.out.println(data);
        System.out.println("*********************************************************");
    }
}
