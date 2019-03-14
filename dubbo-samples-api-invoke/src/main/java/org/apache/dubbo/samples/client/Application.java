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

package org.apache.dubbo.samples.client;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

public class Application {
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-client"));
        reference.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
//        reference.setInterface(GreetingsService.class);
        reference.setGeneric(true);
        reference.setInterface("org.apache.dubbo.samples.api.GreetingsService");
        GenericService genericService = reference.get();


        Object message = genericService.$invoke("sayHi", new String[]{"java.lang.String"}, new Object[]{"aaa"});
        System.out.println(message);
    }
}
