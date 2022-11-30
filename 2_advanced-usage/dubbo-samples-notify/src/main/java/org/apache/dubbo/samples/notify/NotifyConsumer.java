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

package org.apache.dubbo.samples.notify;

import org.apache.dubbo.samples.notify.api.DemoService;
import org.apache.dubbo.samples.notify.api.Notify;
import org.apache.dubbo.samples.notify.impl.NotifyImpl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NotifyConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/notify-consumer.xml");
        context.start();

        DemoService demoService = context.getBean("demoService", DemoService.class);
        NotifyImpl notify = context.getBean("demoCallback", NotifyImpl.class);

        int id = 1;
        String result = demoService.sayHello(id);
        for (int i = 0; i < 10; i++) {
            if (!notify.ret.containsKey(id)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }

        System.out.println("result: " + notify.ret.get(id));
    }
}

