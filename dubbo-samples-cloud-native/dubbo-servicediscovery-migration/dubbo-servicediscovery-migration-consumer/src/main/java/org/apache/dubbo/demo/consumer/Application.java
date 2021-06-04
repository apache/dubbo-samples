/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.demo.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        context.start();

        UpgradeUtil.writeForceInterfaceRule();
        Thread.sleep(5000);

        call(context);

        UpgradeUtil.writeApplicationFirstRule(100);
        Thread.sleep(5000);

        call(context);

    }

    private static void call(ClassPathXmlApplicationContext context) {
        DemoService demoServiceFromNormal = context.getBean("demoServiceFromNormal", DemoService.class);
        System.out.println("result: " + demoServiceFromNormal.sayHello("name"));
        DemoService demoServiceFromService = context.getBean("demoServiceFromService", DemoService.class);
        System.out.println("result: " + demoServiceFromService.sayHello("name"));
        DemoService demoServiceFromDual = context.getBean("demoServiceFromDual", DemoService.class);
        System.out.println("result: " + demoServiceFromDual.sayHello("name"));
    }
}
