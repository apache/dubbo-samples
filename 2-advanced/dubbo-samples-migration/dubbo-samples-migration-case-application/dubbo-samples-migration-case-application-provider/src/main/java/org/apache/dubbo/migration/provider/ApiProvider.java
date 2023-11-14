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
package org.apache.dubbo.migration.provider;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.migration.EmbeddedZooKeeper;
import org.apache.dubbo.migration.GreeterServiceImpl;
import org.apache.dubbo.migration.api.GreeterService;

public class ApiProvider {
    public static void main(String[] args) throws InterruptedException {
        String curProtocol = System.getProperty("dubbo.current.protocol", CommonConstants.DUBBO);
        String zookeeperAddress = System.getProperty("zookeeper.address", "127.0.0.1");
        
        new EmbeddedZooKeeper(2181, false).start();

        ServiceConfig<GreeterService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(GreeterService.class);
        serviceConfig.setRef(new GreeterServiceImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("dubbo-samples-migration-case-application-provider"))
                .registry(new RegistryConfig("zookeeper://" + zookeeperAddress + ":2181"))
                .protocol(new ProtocolConfig(curProtocol))
                .service(serviceConfig)
                .start();
        
        System.out.println("dubbo service started.");
        bootstrap.await();
    }
}
