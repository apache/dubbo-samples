
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

package org.apache.samples.sentinel;

import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import org.apache.samples.sentinel.consumer.ConsumerConfiguration;
import org.apache.samples.sentinel.consumer.FooServiceConsumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Please add the following VM arguments:
 * <pre>
 * -Dcsp.sentinel.api.port=8721
 * -Dproject.name=dubbo-consumer-demo
 * </pre>
 */
public class FooConsumerBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext();
        consumerContext.register(ConsumerConfiguration.class);
        consumerContext.refresh();

        FooServiceConsumer service = consumerContext.getBean(FooServiceConsumer.class);

        for (int i = 0; i < 15; i++) {
            try {
                String message = service.sayHello("dubbo");
                System.out.println("Success: " + message);
            } catch (SentinelRpcException ex) {
                System.out.println("Blocked");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
