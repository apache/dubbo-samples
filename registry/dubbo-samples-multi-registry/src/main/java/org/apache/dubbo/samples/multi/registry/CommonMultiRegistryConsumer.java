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
package org.apache.dubbo.samples.multi.registry;

import org.apache.dubbo.samples.multi.registry.api.DemoService;
import org.apache.dubbo.samples.multi.registry.api.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CommonMultiRegistryConsumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/common-multi-registry-consumer.xml");
        context.start();

        DemoService demoService = context.getBean(DemoService.class);
        HelloService helloService = context.getBean(HelloService.class);
        System.out.println(demoService.get("service form default registry"));
        System.out.println(helloService.sayHello("service form shanghai registry"));
    }
}
