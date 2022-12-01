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

package org.apache.dubbo.samples.basic;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.SslConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.basic.api.DemoService;

import org.junit.Assert;
import org.junit.Test;


public class SslServiceIT {

    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");
    private static String zookeeperPort = System.getProperty("zookeeper.port", "2181");

    private static String zookeeperAddress = "zookeeper://"+ zookeeperHost +":" + zookeeperPort;

    @Test
    public void test() throws Exception {
        SslConfig sslConfig = new SslConfig();

        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
            .application(new ApplicationConfig("dubbo-consumer"))
            .registry(new RegistryConfig(zookeeperAddress))
            .ssl(sslConfig);

        ReferenceConfig<DemoService> reference = new ReferenceConfig<>();
        reference.setInterface(DemoService.class);

        bootstrap.reference(reference);

        bootstrap.start();

        DemoService service = bootstrap.getCache().get(reference);
        String message = service.sayHello("dubbo");
        Assert.assertTrue(message.startsWith("Hello dubbo"));
    }

}
