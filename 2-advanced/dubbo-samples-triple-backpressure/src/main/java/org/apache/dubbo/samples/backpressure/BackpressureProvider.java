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
package org.apache.dubbo.samples.backpressure;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.backpressure.api.BackpressureService;
import org.apache.dubbo.samples.backpressure.impl.BackpressureServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackpressureProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackpressureProvider.class);

    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");

    public static void main(String[] args) {
        ServiceConfig<BackpressureService> service = new ServiceConfig<>();
        service.setInterface(BackpressureService.class);
        service.setRef(new BackpressureServiceImpl());

        String zkAddress = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;
        LOGGER.info("Using ZooKeeper: {}", zkAddress);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("backpressure-provider"))
                .registry(new RegistryConfig(zkAddress))
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, 50051))
                .service(service)
                .start();

        LOGGER.info("BackpressureProvider started on port 50051, waiting for requests...");
        bootstrap.await();
    }
}
