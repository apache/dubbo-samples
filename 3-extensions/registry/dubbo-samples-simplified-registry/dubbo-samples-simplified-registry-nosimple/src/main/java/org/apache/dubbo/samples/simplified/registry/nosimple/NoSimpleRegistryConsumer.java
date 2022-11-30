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

import org.apache.dubbo.common.URL;
import org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.apache.dubbo.common.constants.CommonConstants.RELEASE_KEY;

public class NoSimpleRegistryConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/simplified-consumer.xml");
        context.start();

        DemoService demoService = context.getBean("demoService", DemoService.class);

        printServiceData();

        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }

    private static void printServiceData() {
        List<String> urls = ZkUtil.getChildren(ZkUtil.toUrlPath("consumers"));
        System.out.println("*********************************************************");
        urls.stream().map(URL::decode).forEach(System.out::println);
        System.out.println("contains 'retries':" + urls.get(0).contains("retries"));
        System.out.println("contains 'owner':" + urls.get(0).contains("owner"));
        System.out.println("contains 'actives':" + urls.get(0).contains("actives"));
        System.out.println("contains 'timeout':" + urls.get(0).contains("timeout"));
        System.out.println("contains 'application':" + urls.get(0).contains("application"));
        System.out.println("contains 'version':" + urls.get(0).contains("version"));
        System.out.println("contains 'group':" + urls.get(0).contains("group"));
        System.out.println("contains 'specVersion(default)':" + urls.get(0).contains(RELEASE_KEY));
        System.out.println("*********************************************************");
    }
}
