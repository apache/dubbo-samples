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

package org.apache.dubbo.samples.metrics.prometheus.consumer;

import org.apache.dubbo.samples.metrics.prometheus.api.DemoService;
import org.apache.dubbo.samples.metrics.prometheus.api.DemoService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MetricsConsumer {

    private static Logger logger = LoggerFactory.getLogger(MetricsConsumer.class);


    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("dubbo-demo-consumer.xml");
        DemoService demoService = ctx.getBean(DemoService.class);
        DemoService2 demoService2 = ctx.getBean(DemoService2.class);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                logger.error("sleep failed: ", e);
            }
            System.out.println(demoService.sayHello("Dubbo").getMsg());
            try {
                System.out.println(demoService.randomResponseTime("Dubbo").getMsg());
            } catch (Exception e) {
                logger.error("randomResponseTime failed: ", e);
            }
            try {
                System.out.println(demoService.runTimeException("Dubbo").getMsg());
            } catch (Exception e) {
                logger.error("runTimeException failed: ", e);
            }
            try {
                System.out.println(demoService.timeLimitedMethod("Dubbo").getMsg());
            } catch (Exception e) {
                logger.error("timeLimitedMethod failed: ", e);
            }
        }
    }

}
