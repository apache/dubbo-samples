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
package org.apache.dubbo.samples;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.metadata.MetadataConstants;
import org.apache.dubbo.samples.api.ControlService;
import org.apache.dubbo.samples.api.DemoService1;
import org.apache.dubbo.samples.api.DemoService2;
import org.apache.dubbo.samples.api.DemoService3;
import org.apache.dubbo.samples.impl.ControlServiceImpl;
import org.apache.dubbo.samples.impl.DemoService1Impl;
import org.apache.dubbo.samples.impl.DemoService2Impl;
import org.apache.dubbo.samples.impl.DemoService3Impl;

public class App5 {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty(MetadataConstants.METADATA_PUBLISH_DELAY_KEY, "10");
        ApplicationConfig applicationConfig = new ApplicationConfig("App5");
        applicationConfig.setRegisterMode("instance");

        RegistryConfig registryConfig = new RegistryConfig();
        String zookeeperAddress = System.getProperty("zookeeper.address", "127.0.0.1");
        registryConfig.setAddress("zookeeper://" + zookeeperAddress + ":2181?group=isolated");

        ProtocolConfig protocolConfig1 = new ProtocolConfig("dubbo", -1);

        ServiceConfig<DemoService1> serviceConfig1 = new ServiceConfig<>();
        serviceConfig1.setInterface(DemoService1.class);
        serviceConfig1.setRef(new DemoService1Impl("App5"));
        serviceConfig1.setRegister(false);

        ServiceConfig<DemoService2> serviceConfig2 = new ServiceConfig<>();
        serviceConfig2.setInterface(DemoService2.class);
        serviceConfig2.setRef(new DemoService2Impl("App5"));

        ServiceConfig<DemoService3> serviceConfig3 = new ServiceConfig<>();
        serviceConfig3.setInterface(DemoService3.class);
        serviceConfig3.setRef(new DemoService3Impl("App5"));
        serviceConfig3.setRegister(false);

        ServiceConfig<ControlService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ControlService.class);
        serviceConfig.setRef(new ControlServiceImpl());
        serviceConfig.setVersion("App5");
        
        DubboBootstrap.getInstance()
                .application(applicationConfig)
                .protocol(protocolConfig1)
                .registry(registryConfig)
                .service(serviceConfig)
                .service(serviceConfig1)
                .service(serviceConfig2)
                .service(serviceConfig3)
                .start()
                .await();
    }
}
