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

package org.apache.dubbo.samples.cat.consumer;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.cat.api.DemoService;
import org.apache.dubbo.samples.cat.api.context.CatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableDubbo
@SpringBootApplication
public class CatConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CatConsumer.class);
    private static final String NAME = "cat-consumer";

    @DubboReference(timeout = 10000)
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(CatConsumer.class, args);
        DemoService demoService = ctx.getBean(DemoService.class);

        // Server Tracking
        Transaction t = Cat.newTransaction("Call", NAME);
        try {
            Cat.logEvent("Call.server", NAME);
            Cat.logEvent("Call.app", "business");
            Cat.logEvent("Call.port", "20880");
            CatContext catContext = new CatContext();
            Cat.logRemoteCallClient(catContext);
            logger.info(demoService.sayHello("dubbo and cat", catContext));
        } catch (Exception e) {
            Cat.logError(e);
            t.setStatus(e);
        } finally {
            t.setStatus(Transaction.SUCCESS);
            t.complete();
        }
    }

}
