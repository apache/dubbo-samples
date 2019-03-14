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

package org.apache.dubbo.samples.generic.call;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.samples.generic.call.impl.GenericImplOfHelloService;

/**
 * GenericCallProvider
 */
public class GenericImplProvider {

    public static void main(String[] args) throws Exception {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("generic-impl-provider");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口实现
        GenericService helloService = new GenericImplOfHelloService();

        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ServiceConfig<GenericService> service = new ServiceConfig<>();
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig);
        // 弱类型接口名
        service.setInterface("org.apache.dubbo.samples.generic.call.api.HelloService");
        // 指向一个通用服务实现
        service.setRef(helloService);

        // 暴露及注册服务
        service.export();

        System.in.read();
    }

}
