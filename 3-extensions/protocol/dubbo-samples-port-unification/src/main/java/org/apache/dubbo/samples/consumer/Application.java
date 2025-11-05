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

package org.apache.dubbo.samples.consumer;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.samples.api.GreetingService;

import java.util.HashMap;

public class Application {
    private static String zookeeperHost = System
            .getProperty("zookeeper.address", "127.0.0.1");
    private static String zookeeperPort = System.getProperty("zookeeper.port",
            "2181");

    public static void main(String[] args) throws InterruptedException {
        Client dubboClient = new Client(CommonConstants.DUBBO);
        Client triClient = new Client(CommonConstants.TRIPLE, "FORCE_INTERFACE");
        Thread t1 = new Thread(dubboClient);
        t1.start();
        Thread t2 = new Thread(triClient);
        t2.start();
        t1.join();
        t2.join();
    }

    static class Client implements Runnable {
        private String protocol;
        private String mode = "APPLICATION_FIRST";
        Client(String protocol){
            this.protocol = protocol;
        }

        Client(String protocol, String mode) {
            this(protocol);
            this.mode = mode;
        }

        @Override
        public void run() {
            ReferenceConfig<GreetingService> reference = new ReferenceConfig<>();
            reference.setInterface(GreetingService.class);
            reference.setParameters(new HashMap<>());
            reference.getParameters().put("migration.step", mode);
            reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
            reference.setRegistry(new RegistryConfig(
                    "zookeeper://" + zookeeperHost + ":" + zookeeperPort));
            reference.setProtocol(this.protocol);
            GreetingService service = reference.get();
            String message = service.sayHi(this.protocol);
            System.out.println(message);
        }
    }
}
