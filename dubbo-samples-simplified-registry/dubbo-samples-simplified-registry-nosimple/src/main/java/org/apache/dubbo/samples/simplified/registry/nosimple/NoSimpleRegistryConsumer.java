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

package org.apache.dubbo.samples.simplified.registry.nosimple;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.dubbo.remoting.zookeeper.ZookeeperTransporter;
import org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class NoSimpleRegistryConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/simplified-consumer.xml"});
        context.start();

        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy

        printServiceData();

        while (true) {
            try {
                Thread.sleep(1000);
                String hello = demoService.sayHello("world"); // call remote method
                System.out.println(hello); // get result

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }

    private static void printServiceData() {
        ZookeeperClient zookeeperClient = ExtensionLoader.getExtensionLoader(ZookeeperTransporter.class).getExtension("curator").connect(new URL("zookeeper", "127.0.0.1", 2181));
        List<String> urls = zookeeperClient.getChildren(ZkUtil.toUrlPath("consumers"));
        System.out.println("*********************************************************");
        System.out.println(urls);
        System.out.println("simple contain 'retries':" + urls.get(0).contains("retries"));
        System.out.println("simple contain 'owner':" + urls.get(0).contains("owner"));
        System.out.println("simple contain 'actives':" + urls.get(0).contains("actives"));
        System.out.println("simple contain 'timeout':" + urls.get(0).contains("timeout"));
        System.out.println("simple contain 'application':" + urls.get(0).contains("application"));
        System.out.println("simple contain 'version':" + urls.get(0).contains("version"));
        System.out.println("simple contain 'group':" + urls.get(0).contains("group"));
        System.out.println("simple contain 'specVersion(default)':" + urls.get(0).contains(CommonConstants.RELEASE_KEY));
        System.out.println("*********************************************************");
    }
}
