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

package org.apache.dubbo.samples.metadatareport.local.properties;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.dubbo.remoting.zookeeper.ZookeeperTransporter;
import org.apache.dubbo.samples.metadatareport.local.properties.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MetadataLocalpropertiesProvider {

    public static void main(String[] args) throws Exception {
        EmbeddedZooKeeper embeddedZooKeeper = new EmbeddedZooKeeper(2181, false);
        embeddedZooKeeper.start();

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/metadata-provider.xml"});
        context.start();

        printServiceData();
        System.in.read(); // press any key to exit
        embeddedZooKeeper.stop();
    }

    private static void printServiceData() throws InterruptedException {
        // get service data(provider) from zookeeper .
        Thread.sleep(3000);
        ZookeeperClient zookeeperClient = ExtensionLoader.getExtensionLoader(ZookeeperTransporter.class).getExtension("curator").connect(new URL("zookeeper", "127.0.0.1", 2181));
        String data = zookeeperClient.getContent(ZkUtil.getNodePath(new MetadataIdentifier(DemoService.class.getName(), null, null, CommonConstants.PROVIDER_SIDE, "metadatareport-local-properties-provider2")));
        System.out.println("*********************************************************");
        System.out.println("Dubbo store metadata into special store(as zk,redis) when local xml:");
        System.out.println(data);
        System.out.println("*********************************************************");
    }

}
