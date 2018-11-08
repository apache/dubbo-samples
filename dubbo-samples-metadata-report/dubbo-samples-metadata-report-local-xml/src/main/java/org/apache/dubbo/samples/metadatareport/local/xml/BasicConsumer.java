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

package org.apache.dubbo.samples.metadatareport.local.xml;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.metadata.identifier.ConsumerMetadataIdentifier;
import org.apache.dubbo.metadata.identifier.ProviderMetadataIdentifier;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.dubbo.remoting.zookeeper.ZookeeperTransporter;
import org.apache.dubbo.samples.metadatareport.local.xml.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BasicConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/metadata-consumer.xml"});
        context.start();

        printServiceData();

        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy

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
        // get service data(consumer) from zookeeper.
        ZookeeperClient zookeeperClient = ExtensionLoader.getExtensionLoader(ZookeeperTransporter.class).getExtension("curator").connect(new URL("zookeeper", "127.0.0.1", 2181));
        String data = zookeeperClient.getContent(ZkUtil.getNodePath(new ConsumerMetadataIdentifier(DemoService.class.getName(), null, null, "metadatareport-local-xml-consumer")));
        System.out.println("*********************************************************");
        System.out.println("Dubbo store consumer param into special store(as zk,redis) when local xml:");
        System.out.println(data);
        System.out.println("*********************************************************");
    }
}
