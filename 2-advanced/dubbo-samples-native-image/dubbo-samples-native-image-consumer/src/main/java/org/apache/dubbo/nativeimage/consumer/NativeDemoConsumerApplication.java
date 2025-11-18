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
package org.apache.dubbo.nativeimage.consumer;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.nativeimage.DemoService;
import org.apache.dubbo.nativeimage.HelloRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@SpringBootApplication(scanBasePackages = {"org.apache.dubbo.nativeimage.consumer"})
@EnableDubbo(scanBasePackages = {"org.apache.dubbo.nativeimage.consumer"})
public class NativeDemoConsumerApplication {

    /**
     * should change to the provider's ip address and port.
     */
    @DubboReference(url = "tri://172.17.0.2:50052?serialization=fastjson2")
    private DemoService demoService;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(NativeDemoConsumerApplication.class, args);
        NativeDemoConsumerApplication application = context.getBean(NativeDemoConsumerApplication.class);
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("dubbo consumer application started, The time taken to start the application is "
                + (System.currentTimeMillis() - runtimeMXBean.getStartTime()) +" ms");

        long startCallTime =  System.currentTimeMillis();
        String result = application.doSayHello("world");
        System.out.println("The time taken for the first call is "
                + (System.currentTimeMillis() - startCallTime) +" ms");
        System.out.println("result: " + result);
    }

    public String doSayHello(String name) {
        return demoService.sayHello(new HelloRequest(name)).getResponse();
    }

}
