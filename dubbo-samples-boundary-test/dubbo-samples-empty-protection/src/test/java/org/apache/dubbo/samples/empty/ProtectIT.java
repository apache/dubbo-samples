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
package org.apache.dubbo.samples.empty;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.qos.command.impl.Offline;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.EmbeddedZooKeeper;
import org.apache.dubbo.samples.api.GreetingsService;
import org.apache.dubbo.samples.provider.GreetingsServiceImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ProtectIT {
    @After
    public void after() {
        FrameworkModel.destroyAll();
    }

    @Test
    public void testProtect() throws InterruptedException {
        new EmbeddedZooKeeper(2181, false).start();

        ServiceConfig<GreetingsService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(GreetingsService.class);
        serviceConfig.setRef(new GreetingsServiceImpl());
        serviceConfig.setApplication(new ApplicationConfig("provider"));
        serviceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181?enable-empty-protection=true"));
        serviceConfig.export();

        ReferenceConfig<GreetingsService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(GreetingsService.class);
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181?enable-empty-protection=true"));
        referenceConfig.setScope("remote");
        GreetingsService greetingsService = referenceConfig.get();

        Assert.assertEquals("hi, dubbo", greetingsService.sayHi("dubbo"));

        new Offline(FrameworkModel.defaultModel()).offline("org.apache.dubbo.samples.api.GreetingsService");
        Thread.sleep(1000);

        Assert.assertEquals("hi, dubbo", greetingsService.sayHi("dubbo"));
    }
}
