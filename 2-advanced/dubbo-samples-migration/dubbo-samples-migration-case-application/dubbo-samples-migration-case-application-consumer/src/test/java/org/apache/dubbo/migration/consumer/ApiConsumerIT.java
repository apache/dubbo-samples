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

package org.apache.dubbo.migration.consumer;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.migration.api.GreeterService;
import org.apache.dubbo.migration.api.HelloReply;
import org.apache.dubbo.migration.api.HelloRequest;

import org.junit.Assert;
import org.junit.Test;

/**
 * Consumer test side
 */
public class ApiConsumerIT {
    
    
    @Test(timeout = 30000)
    public void consumeInvoke() {
        String curProtocol = System.getProperty("dubbo.current.protocol", CommonConstants.DUBBO);
        String zookeeperAddress = System.getProperty("zookeeper.address", "127.0.0.1");
        
        ReferenceConfig<GreeterService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(GreeterService.class);
        referenceConfig.setCheck(false);
        referenceConfig.setProtocol(curProtocol);
        referenceConfig.setLazy(true);
        referenceConfig.setTimeout(100000);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("dubbo-samples-migration-case-application-consumer"))
                .registry(new RegistryConfig("zookeeper://" + zookeeperAddress + ":2181"))
                .reference(referenceConfig)
                .start();

        GreeterService greeterService = referenceConfig.get();
        HelloRequest helloRequest = new HelloRequest();
        helloRequest.setName(curProtocol);
        final HelloReply reply = greeterService.sayHello(helloRequest);
        
        Assert.assertEquals("Hello " + curProtocol, reply.getMessage());
    }
}
