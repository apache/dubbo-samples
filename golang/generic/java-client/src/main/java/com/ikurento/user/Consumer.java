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

package com.ikurento.user;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

public class Consumer {
    public static void main(String[] args) {

         System.out.println("\n\n\nstart to generic invoke");
         ApplicationConfig applicationConfig = new ApplicationConfig();
         ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
         applicationConfig.setName("UserProviderGer");
         reference.setApplication(applicationConfig);
         RegistryConfig registryConfig = new RegistryConfig();
         registryConfig.setAddress("zookeeper://127.0.0.1:2181");
         reference.setRegistry(registryConfig);
         reference.setGeneric(true);
         reference.setInterface("com.ikurento.user.UserProvider");
         GenericService genericService = reference.get();
         Object[] parameterArgs = new Object[]{"A003"};
         Object result = genericService.$invoke("GetUser", null , parameterArgs);
         System.out.println("res: " + result);

         System.out.println("\n\n\nstart to generic invoke1");
         User user = new User();
         user.setName("Patrick");
         user.setId("id");
         user.setAge(10);
         parameterArgs = new Object[]{user};
         Object result1 = genericService.$invoke("queryUser", new String[]{"com.ikurento.user.User"} , parameterArgs);
         System.out.println("res: " + result1);
    }

}
