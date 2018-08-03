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
package com.alibaba.dubbo.samples.generic;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.dubbo.samples.generic.impl.GenericServiceImpl;

import java.io.IOException;

public class APIGenericProvider {
    public static void main(String[] args) throws IOException {
        new EmbeddedZooKeeper(2181, false).start();

        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-generic-provider");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        application.setRegistry(registry);

        GenericService genericService = new GenericServiceImpl();

        ServiceConfig<GenericService> service = new ServiceConfig<GenericService>();
        // 弱类型接口名
        service.setApplication(application);
        service.setInterface("com.alibaba.dubbo.samples.generic.api.HelloService");
        service.setRef(genericService);
        service.export();

        ServiceConfig<GenericService> service2 = new ServiceConfig<GenericService>();
        // 弱类型接口名
        service2.setApplication(application);
        service2.setInterface("com.alibaba.dubbo.samples.generic.api.HiService");
        service2.setRef(genericService);
        service2.export();
        System.in.read();
    }
}
