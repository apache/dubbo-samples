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

import org.apache.dubbo.config.MetadataReportConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.api.GreetService;
import org.junit.Assert;
import org.junit.Test;


public class GreetServiceIT {

    @Test
    public void test(){
        System.setProperty("dubbo.application.qos-enable","false");
        RegistryConfig registryConfig = new RegistryConfig(GreetService.NACOS_ADDR);
        registryConfig.setRegisterMode("instance");
        ReferenceConfig<GreetService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(GreetService.class);
        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application("GreetConsumerApplication")
                .registry(registryConfig)
                .metadataReport(new MetadataReportConfig(GreetService.NACOS_NAMESPACE_2_PATH))
                .protocol(new ProtocolConfig("dubbo", GreetService.PORT))
                .reference(referenceConfig);
        bootstrap.start();
        Assert.assertEquals("Hello:world",referenceConfig.get().greet("world"));
    }
}
