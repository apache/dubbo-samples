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

package org.apache.dubbo.samples.test;

import cn.hutool.http.HttpUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

public class Provider {

    public static void main(String[] args) throws Exception {
        System.setProperty("dubbo.registry.address", "zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/provider.xml");
        context.start();

        String qosResult = HttpUtil.get("http://127.0.0.1:22123/ls");
        System.out.println(qosResult);
        boolean matched = false;
        for (String line : qosResult.split("\n")) {
            if (line.contains("MetadataService") && line.contains("zookeeper")) {
                throw new Error("MetadataService should not be registered.");
            }
            if (line.contains("org.apache.dubbo.samples.test.api.DemoService") && line.contains("zookeeper-A(Y)/zookeeper-I(Y)")) {
                matched = true;
            }
        }
        if (!matched) {
            throw new Error("org.apache.dubbo.samples.test.api.DemoService should be registered.");
        }

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
