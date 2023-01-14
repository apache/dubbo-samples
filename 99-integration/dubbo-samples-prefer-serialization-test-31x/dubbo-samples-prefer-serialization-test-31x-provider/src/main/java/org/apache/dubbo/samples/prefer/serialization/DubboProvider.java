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
package org.apache.dubbo.samples.prefer.serialization;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.prefer.serialization.api.DemoService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DubboProvider {
    private static final String FASTJSON_SERIALIZATION = "fastjson";
    private static final String PROTOSTUFF_SERIALIZATION = "protostuff";
    private static final String GSON_SERIALIZATION = "gson";
    private static final String FST_SERIALIZATION = "fst";

    public static void main(String[] args) throws InterruptedException {
        FrameworkModel frameworkModel = new FrameworkModel();

        // in 3.1.x, service config's serialization > protocol config's serialization
        // in 3.2.x, service config's prefer serialization > service config's serialization >
        //           protocol config's prefer serialization > protocol config's serialization

        // protocol config set serialization as null, prefer serialization is set as null
        // service config set serialization as null, prefer serialization is set as null
        // in 3.1.x, will use hessian2 serialization
        // in 3.2.x, will use fastjson2 serialization
        exportService(frameworkModel,
                null,
                null,
                null,
                null,
                "1.0.0");
        System.out.println("1.0.0 service exported");

        // protocol config set serialization as protostuff, prefer serialization is set as null
        // service config set serialization as null, prefer serialization is set as null
        // in 3.1.x, will use protostuff serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                PROTOSTUFF_SERIALIZATION,
                null,
                null,
                null,
                "2.0.0");
        System.out.println("2.0.0 service exported");

        // protocol config set serialization as null, prefer serialization is set as null
        // service config set serialization as protostuff, prefer serialization is set as null
        // in 3.1.x, will use protostuff serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                null,
                PROTOSTUFF_SERIALIZATION,
                null,
                null,
                "3.0.0");
        System.out.println("3.0.0 service exported");

        // protocol config set serialization as null, prefer serialization is set as protostuff
        // service config set serialization as null, prefer serialization is set as null
        // in 3.1.x, will use hessian2 serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                null,
                null,
                PROTOSTUFF_SERIALIZATION,
                null,
                "4.0.0");
        System.out.println("4.0.0 service exported");

        // protocol config set serialization as null, prefer serialization is set as null
        // service config set serialization as null, prefer serialization is set as protostuff
        // in 3.1.x, will use hessian2 serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                null,
                null,
                null,
                PROTOSTUFF_SERIALIZATION,
                "5.0.0");
        System.out.println("5.0.0 service exported");

        // protocol config set serialization as protostuff, prefer serialization is set as null
        // service config set serialization as fastjson, prefer serialization is set as null
        // in 3.1.x, will use fastjson serialization
        // in 3.2.x, will use fastjson serialization
        exportService(frameworkModel,
                PROTOSTUFF_SERIALIZATION,
                FASTJSON_SERIALIZATION,
                null,
                null,
                "6.0.0");
        System.out.println("6.0.0 service exported");

        // protocol config set serialization as null, prefer serialization is set as protostuff
        // service config set serialization as null, prefer serialization is set as fastjson
        // in 3.1.x, will use hessian2 serialization
        // in 3.2.x, will use fastjson serialization
        exportService(frameworkModel,
                null,
                null,
                PROTOSTUFF_SERIALIZATION,
                FASTJSON_SERIALIZATION,
                "7.0.0");
        System.out.println("7.0.0 service exported");

        // protocol config set serialization as protostuff, prefer serialization is set as fastjson
        // service config set serialization as null, prefer serialization is set as null
        // in 3.1.x, will use protostuff serialization
        // in 3.2.x, will use fastjson serialization
        exportService(frameworkModel,
                PROTOSTUFF_SERIALIZATION,
                null,
                FASTJSON_SERIALIZATION,
                null,
                "8.0.0");
        System.out.println("8.0.0 service exported");

        // protocol config set serialization as null, prefer serialization is set as null
        // service config set serialization as protostuff, prefer serialization is set as fastjson
        // in 3.1.x, will use protostuff serialization
        // in 3.2.x, will use fastjson serialization
        exportService(frameworkModel,
                PROTOSTUFF_SERIALIZATION,
                null,
                FASTJSON_SERIALIZATION,
                null,
                "9.0.0");
        System.out.println("9.0.0 service exported");

        // protocol config set serialization as fastjson, prefer serialization is set as null
        // service config set serialization as null, prefer serialization is set as protostuff
        // in 3.1.x, will use fastjson serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                FASTJSON_SERIALIZATION,
                null,
                null,
                PROTOSTUFF_SERIALIZATION,
                "10.0.0");
        System.out.println("10.0.0 service exported");

        // protocol config set serialization as null, prefer serialization is set as fastjson
        // service config set serialization as protostuff, prefer serialization is set as null
        // in 3.1.x, will use protostuff serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                null,
                PROTOSTUFF_SERIALIZATION,
                FASTJSON_SERIALIZATION,
                null,
                "11.0.0");
        System.out.println("11.0.0 service exported");

        // protocol config set serialization as gson, prefer serialization is set as fastjson
        // service config set serialization as protostuff, prefer serialization is set as null
        // in 3.1.x, will use protostuff serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                GSON_SERIALIZATION,
                PROTOSTUFF_SERIALIZATION,
                FASTJSON_SERIALIZATION,
                null,
                "12.0.0");
        System.out.println("12.0.0 service exported");

        // protocol config set serialization as gson, prefer serialization is set as fastjson
        // service config set serialization as null, prefer serialization is set as protostuff
        // in 3.1.x, will use gson serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                GSON_SERIALIZATION,
                null,
                FASTJSON_SERIALIZATION,
                PROTOSTUFF_SERIALIZATION,
                "13.0.0");
        System.out.println("13.0.0 service exported");

        // protocol config set serialization as gson, prefer serialization is set as null
        // service config set serialization as fastjson, prefer serialization is set as protostuff
        // in 3.1.x, will use fastjson serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                GSON_SERIALIZATION,
                FASTJSON_SERIALIZATION,
                null,
                PROTOSTUFF_SERIALIZATION,
                "14.0.0");
        System.out.println("14.0.0 service exported");

        // protocol config set serialization as null, prefer serialization is set as gson
        // service config set serialization as fastjson, prefer serialization is set as protostuff
        // in 3.1.x, will use fastjson serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                null,
                FASTJSON_SERIALIZATION,
                GSON_SERIALIZATION,
                PROTOSTUFF_SERIALIZATION,
                "15.0.0");
        System.out.println("15.0.0 service exported");

        // protocol config set serialization as fst, prefer serialization is set as gson
        // service config set serialization as fastjson, prefer serialization is set as protostuff
        // in 3.1.x, will use fastjson serialization
        // in 3.2.x, will use protostuff serialization
        exportService(frameworkModel,
                FST_SERIALIZATION,
                FASTJSON_SERIALIZATION,
                GSON_SERIALIZATION,
                PROTOSTUFF_SERIALIZATION,
                "16.0.0");
        System.out.println("16.0.0 service exported");

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }

    private static final AtomicInteger index = new AtomicInteger(0);

    private static void exportService(FrameworkModel frameworkModel,
                                      String serializationInProtocolConfig,
                                      String serializationInServiceConfig,
                                      String preferSerializationInProtocolConfig,
                                      String preferSerializationInServiceConfig,
                                      String serviceVersion) {

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setSerialization(serializationInProtocolConfig);
        protocolConfig.setPreferSerialization(preferSerializationInProtocolConfig);

        ServiceConfig<DemoService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(DemoService.class);
        serviceConfig.setVersion(serviceVersion);
        serviceConfig.setRef(new DemoServiceImpl());
        serviceConfig.setSerialization(serializationInServiceConfig);
        serviceConfig.setPreferSerialization(preferSerializationInServiceConfig);


        DubboBootstrap bootstrap = DubboBootstrap.getInstance(frameworkModel.newApplication());
        bootstrap.registry(new RegistryConfig("zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181"))
                .application(new ApplicationConfig("serialization-test-" + index.incrementAndGet()))
                .service(serviceConfig)
                .protocol(protocolConfig)
                .start();
    }
}
