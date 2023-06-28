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
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

import io.dubbo.test.DemoService2;
import io.dubbo.test.DemoService2Impl;

public class Provider {
    public static void main(String[] args) {
        ServiceConfig<DemoService1> serviceConfig1 = new ServiceConfig<>();
        serviceConfig1.setInterface(DemoService1.class);
        serviceConfig1.setVersion("origin-hessian2");
        serviceConfig1.setSerialization("hessian2");
        serviceConfig1.setRef(new DemoService1Impl());

        ServiceConfig<DemoService1> serviceConfig2 = new ServiceConfig<>();
        serviceConfig2.setInterface(DemoService1.class);
        serviceConfig2.setVersion("origin-fastjson2");
        serviceConfig2.setSerialization("fastjson2");
        serviceConfig2.setRef(new DemoService1Impl());

        ServiceConfig<GenericService> serviceConfig3 = new ServiceConfig<>();
        serviceConfig3.setInterface(DemoService1.class.getName());
        serviceConfig3.setVersion("generic-hessian2");
        serviceConfig3.setSerialization("hessian2");
        serviceConfig3.setGeneric("true");
        serviceConfig3.setRef(new GenericImpl(new DemoService1Impl()));

        ServiceConfig<GenericService> serviceConfig4 = new ServiceConfig<>();
        serviceConfig4.setInterface(DemoService1.class.getName());
        serviceConfig4.setVersion("generic-fastjson2");
        serviceConfig4.setSerialization("fastjson2");
        serviceConfig4.setGeneric("true");
        serviceConfig4.setRef(new GenericImpl(new DemoService1Impl()));

        ServiceConfig<GenericService> serviceConfig5 = new ServiceConfig<>();
        serviceConfig5.setInterface(DemoService1.class.getName());
        serviceConfig5.setVersion("bean-hessian2");
        serviceConfig5.setSerialization("hessian2");
        serviceConfig5.setGeneric("bean");
        serviceConfig5.setRef(new BeanGenericImpl(new DemoService1Impl()));

        ServiceConfig<GenericService> serviceConfig6 = new ServiceConfig<>();
        serviceConfig6.setInterface(DemoService1.class.getName());
        serviceConfig6.setVersion("bean-fastjson2");
        serviceConfig6.setSerialization("fastjson2");
        serviceConfig6.setGeneric("bean");
        serviceConfig6.setRef(new BeanGenericImpl(new DemoService1Impl()));

        ServiceConfig<DemoService2> serviceConfig7 = new ServiceConfig<>();
        serviceConfig7.setInterface(DemoService2.class);
        serviceConfig7.setVersion("origin-hessian2");
        serviceConfig7.setSerialization("hessian2");
        serviceConfig7.setRef(new DemoService2Impl());

        ServiceConfig<DemoService2> serviceConfig8 = new ServiceConfig<>();
        serviceConfig8.setInterface(DemoService2.class);
        serviceConfig8.setVersion("origin-fastjson2");
        serviceConfig8.setSerialization("fastjson2");
        serviceConfig8.setRef(new DemoService2Impl());

        ServiceConfig<GenericService> serviceConfig9 = new ServiceConfig<>();
        serviceConfig9.setInterface(DemoService2.class.getName());
        serviceConfig9.setVersion("generic-hessian2");
        serviceConfig9.setSerialization("hessian2");
        serviceConfig9.setGeneric("true");
        serviceConfig9.setRef(new GenericImpl(new DemoService2Impl()));

        ServiceConfig<GenericService> serviceConfig10 = new ServiceConfig<>();
        serviceConfig10.setInterface(DemoService2.class.getName());
        serviceConfig10.setVersion("generic-fastjson2");
        serviceConfig10.setSerialization("fastjson2");
        serviceConfig10.setGeneric("true");
        serviceConfig10.setRef(new GenericImpl(new DemoService2Impl()));

        ServiceConfig<GenericService> serviceConfig11 = new ServiceConfig<>();
        serviceConfig11.setInterface(DemoService2.class.getName());
        serviceConfig11.setVersion("bean-hessian2");
        serviceConfig11.setSerialization("hessian2");
        serviceConfig11.setGeneric("bean");
        serviceConfig11.setRef(new BeanGenericImpl(new DemoService2Impl()));

        ServiceConfig<GenericService> serviceConfig12 = new ServiceConfig<>();
        serviceConfig12.setInterface(DemoService2.class.getName());
        serviceConfig12.setVersion("bean-fastjson2");
        serviceConfig12.setSerialization("fastjson2");
        serviceConfig12.setGeneric("bean");
        serviceConfig12.setRef(new BeanGenericImpl(new DemoService2Impl()));

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setSerializeCheckStatus("STRICT");
        applicationConfig.setTrustSerializeClassLevel(3);
        applicationConfig.setName("provider");
        DubboBootstrap.getInstance()
                .application(applicationConfig)
                .registry(new RegistryConfig("zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181"))
                .service(serviceConfig1)
                .service(serviceConfig2)
                .service(serviceConfig3)
                .service(serviceConfig4)
                .service(serviceConfig5)
                .service(serviceConfig6)
                .service(serviceConfig7)
                .service(serviceConfig8)
                .service(serviceConfig9)
                .service(serviceConfig10)
                .service(serviceConfig11)
                .service(serviceConfig12)
                .start()
                .await();
    }
}
