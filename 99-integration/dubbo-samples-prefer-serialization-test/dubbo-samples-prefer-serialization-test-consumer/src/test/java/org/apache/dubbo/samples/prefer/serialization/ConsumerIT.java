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

import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.prefer.serialization.api.DemoService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


// in 3.1.x, service config's serialization > protocol config's serialization
// in 3.2.x, service config's prefer serialization > service config's serialization >
//           protocol config's prefer serialization > protocol config's serialization
public class ConsumerIT {
    private static FrameworkModel frameworkModel;
    private static ApplicationModel applicationModel;

    private static final Byte HESSIAN2_SERIALIZATION_ID = FrameworkModel.defaultModel().getExtensionLoader(Serialization.class).getExtension("hessian2", false).getContentTypeId();
    private static final Byte FASTJSON2_SERIALIZATION_ID = FrameworkModel.defaultModel().getExtensionLoader(Serialization.class).getExtension("fastjson2", false).getContentTypeId();
    private static final Byte GSON_SERIALIZATION_ID = FrameworkModel.defaultModel().getExtensionLoader(Serialization.class).getExtension("gson", false).getContentTypeId();
    private static final Byte FASTJSON_SERIALIZATION_ID = FrameworkModel.defaultModel().getExtensionLoader(Serialization.class).getExtension("fastjson", false).getContentTypeId();
    private static final Byte JAVA_SERIALIZATION_ID = FrameworkModel.defaultModel().getExtensionLoader(Serialization.class).getExtension("java", false).getContentTypeId();

    @BeforeClass
    public static void setup() {
        frameworkModel = new FrameworkModel();
        applicationModel = frameworkModel.newApplication();
        DubboBootstrap bootstrap = DubboBootstrap.getInstance(applicationModel);
        bootstrap.registry(new RegistryConfig("zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181"))
                .application(new ApplicationConfig("serialization-test-consumer"))
                .start();
    }

    @AfterClass
    public static void teardown() {
        frameworkModel.destroy();
    }

    // protocol config set serialization as null, prefer serialization is set as null
    // service config set serialization as null, prefer serialization is set as null
    // in 3.1.x, will use hessian2 serialization
    // in 3.2.x, will use fastjson2 serialization
    @Test
    public void test1() {
        DemoService demoService = getDemoService("1.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(FASTJSON2_SERIALIZATION_ID)));
    }


    // protocol config set serialization as java, prefer serialization is set as null
    // service config set serialization as null, prefer serialization is set as null
    // in 3.1.x, will use java serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test2() {
        DemoService demoService = getDemoService("2.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as null, prefer serialization is set as null
    // service config set serialization as java, prefer serialization is set as null
    // in 3.1.x, will use java serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test3() {
        DemoService demoService = getDemoService("3.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as null, prefer serialization is set as java
    // service config set serialization as null, prefer serialization is set as null
    // in 3.1.x, will use hessian2 serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test4() {
        DemoService demoService = getDemoService("4.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as null, prefer serialization is set as null
    // service config set serialization as null, prefer serialization is set as java
    // in 3.1.x, will use hessian2 serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test5() {
        DemoService demoService = getDemoService("5.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as java, prefer serialization is set as null
    // service config set serialization as fastjson, prefer serialization is set as null
    // in 3.1.x, will use fastjson serialization
    // in 3.2.x, will use fastjson serialization
    @Test
    public void test6() {
        DemoService demoService = getDemoService("6.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(FASTJSON_SERIALIZATION_ID)));
    }

    // protocol config set serialization as null, prefer serialization is set as java
    // service config set serialization as null, prefer serialization is set as fastjson
    // in 3.1.x, will use hessian2 serialization
    // in 3.2.x, will use fastjson serialization
    @Test
    public void test7() {
        DemoService demoService = getDemoService("7.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(FASTJSON_SERIALIZATION_ID)));
    }

    // protocol config set serialization as java, prefer serialization is set as fastjson
    // service config set serialization as null, prefer serialization is set as null
    // in 3.1.x, will use java serialization
    // in 3.2.x, will use fastjson serialization
    @Test
    public void test8() {
        DemoService demoService = getDemoService("8.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(FASTJSON_SERIALIZATION_ID)));
    }

    // protocol config set serialization as null, prefer serialization is set as null
    // service config set serialization as java, prefer serialization is set as fastjson
    // in 3.1.x, will use java serialization
    // in 3.2.x, will use fastjson serialization
    @Test
    public void test9() {
        DemoService demoService = getDemoService("9.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(FASTJSON_SERIALIZATION_ID)));
    }

    // protocol config set serialization as fastjson, prefer serialization is set as null
    // service config set serialization as null, prefer serialization is set as java
    // in 3.1.x, will use fastjson serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test10() {
        DemoService demoService = getDemoService("10.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as null, prefer serialization is set as fastjson
    // service config set serialization as java, prefer serialization is set as null
    // in 3.1.x, will use java serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test11() {
        DemoService demoService = getDemoService("11.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as gson, prefer serialization is set as fastjson
    // service config set serialization as java, prefer serialization is set as null
    // in 3.1.x, will use java serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test12() {
        DemoService demoService = getDemoService("12.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as gson, prefer serialization is set as fastjson
    // service config set serialization as null, prefer serialization is set as java
    // in 3.1.x, will use gson serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test13() {
        DemoService demoService = getDemoService("13.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as gson, prefer serialization is set as null
    // service config set serialization as fastjson, prefer serialization is set as java
    // in 3.1.x, will use fastjson serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test14() {
        DemoService demoService = getDemoService("14.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as null, prefer serialization is set as gson
    // service config set serialization as fastjson, prefer serialization is set as java
    // in 3.1.x, will use fastjson serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test15() {
        DemoService demoService = getDemoService("15.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    // protocol config set serialization as fst, prefer serialization is set as gson
    // service config set serialization as fastjson, prefer serialization is set as java
    // in 3.1.x, will use fastjson serialization
    // in 3.2.x, will use java serialization
    @Test
    public void test16() {
        DemoService demoService = getDemoService("16.0.0");
        Assert.assertEquals("Hello world", demoService.sayHello("world"));
        Assert.assertTrue(SerializationWrapper.getUsedSerialization().stream().allMatch(s -> s.equals(JAVA_SERIALIZATION_ID)));
    }

    private DemoService getDemoService(String version) {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>(applicationModel.newModule());
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setVersion(version);
        try {
            return referenceConfig.get();
        } finally {
            SerializationWrapper.getUsedSerialization().clear();
        }
    }
}
