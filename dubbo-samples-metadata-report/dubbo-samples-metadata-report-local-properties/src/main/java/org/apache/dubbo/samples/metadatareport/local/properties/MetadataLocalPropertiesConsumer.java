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
import org.apache.dubbo.samples.metadatareport.local.properties.api.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MetadataLocalPropertiesConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/metadata-consumer.xml");
        context.start();

        DemoService demoService = (DemoService) context.getBean("demoService");

        printServiceData();

        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }

    private static void printServiceData() throws Exception {
        Thread.sleep(3000);
        System.out.println("*********************************************************");
        System.out.println("service metadata:");
        System.out.println(ZkUtil.getMetadata("/dubbo", DemoService.class.getName(), CommonConstants.CONSUMER_SIDE,
                "metadatareport-local-properties-consumer"));
        System.out.println("*********************************************************");
    }
}
