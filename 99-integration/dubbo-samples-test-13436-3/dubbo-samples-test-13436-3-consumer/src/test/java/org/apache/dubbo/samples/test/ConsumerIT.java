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
package org.apache.dubbo.samples.test;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ConsumerIT {
    @Test
    public void test() {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("org.apache.dubbo.samples.test.DemoService");
        reference.setGeneric("true");

        DubboBootstrap.getInstance()
                .registry(new RegistryConfig("zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181"))
                .application(new ApplicationConfig("consumer"))
                .reference(reference)
                .start();

        GenericService genericService = reference.get();
        Map<String, String> map = new HashMap<>();
        map.put("name", "dubbo");
        Assert.assertEquals("hello, dubbo", genericService.$invoke("sayHello", new String[]{"org.apache.dubbo.samples.test.User"}, new Object[]{map}));
    }
}
