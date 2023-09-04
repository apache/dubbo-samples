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
package org.apache.dubbo.test;

import org.apache.dubbo.common.beanutil.JavaBeanDescriptor;
import org.apache.dubbo.common.beanutil.JavaBeanSerializeUtil;
import org.apache.dubbo.common.utils.PojoUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.service.GenericService;

import io.dubbo.test.DemoService2;
import io.dubbo.test.NotSerializable;
import io.dubbo.test.Parent;
import io.dubbo.test.User;
import io.dubbo.test.User2;
import io.dubbo.test2.OthersSerializable;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;

public class CheckIoIT {
    @BeforeClass
    public static void setup() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setSerializeCheckStatus("STRICT");
        applicationConfig.setTrustSerializeClassLevel(3);
        applicationConfig.setName("consumer");
        DubboBootstrap.getInstance()
                .application(applicationConfig)
                .registry(new RegistryConfig("zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181"))
                .start();
    }

    @AfterClass
    public static void tearDown() {
        DubboBootstrap.reset();
        FrameworkModel.destroyAll();
    }

    @Test
    public void testOriginHessian2() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("origin-hessian2");

        DemoService2 demoService2 = referenceConfig.get();

        testAll(demoService2);
    }

    @Test
    public void testOriginFastjson2() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("origin-fastjson2");

        DemoService2 demoService2 = referenceConfig.get();

        testAll(demoService2);
    }

    @Test
    public void testGenericHessian2() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("generic-hessian2");

        DemoService2 demoService2 = referenceConfig.get();

        testAll(demoService2);
    }

    @Test
    public void testGenericFastjson2() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("generic-fastjson2");

        DemoService2 demoService2 = referenceConfig.get();

        testAll(demoService2);
    }

    @Test
    public void testBeanHessian2() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("bean-hessian2");

        DemoService2 demoService2 = referenceConfig.get();

        testAll(demoService2);
    }

    @Test
    public void testBeanFastjson2() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("bean-fastjson2");

        DemoService2 demoService2 = referenceConfig.get();

        testAll(demoService2);
    }

    @Test
    public void testOriginHessian2Generic() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("origin-hessian2");
        referenceConfig.setGeneric("true");

        DemoService2 demoService2 = referenceConfig.get();

        testGeneric(demoService2);
    }

    @Test
    public void testOriginFastjson2Generic() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("origin-fastjson2");
        referenceConfig.setGeneric("true");

        DemoService2 demoService2 = referenceConfig.get();

        testGeneric(demoService2);
    }

    @Test
    public void testOriginHessian2Bean() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("origin-hessian2");
        referenceConfig.setGeneric("bean");

        DemoService2 demoService2 = referenceConfig.get();

        testBeanGeneric(demoService2);
    }

    @Test
    public void testOriginFastjson2Bean() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("origin-fastjson2");
        referenceConfig.setGeneric("bean");

        DemoService2 demoService2 = referenceConfig.get();

        testBeanGeneric(demoService2);
    }

    @Test
    public void testGenericHessian2Generic() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("generic-hessian2");
        referenceConfig.setGeneric("true");

        DemoService2 demoService2 = referenceConfig.get();

        testGeneric(demoService2);
    }

    @Test
    public void testGenericFastjson2Generic() {
        ReferenceConfig<DemoService2> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService2.class);
        referenceConfig.setVersion("generic-fastjson2");
        referenceConfig.setGeneric("true");

        DemoService2 demoService2 = referenceConfig.get();

        testGeneric(demoService2);
    }

    private static void testGeneric(DemoService2 demoService2) {
        DemoService2 demoServiceNew = (DemoService2) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{DemoService2.class}, (proxy, method, args) -> {
            GenericService genericService = (GenericService) demoService2;
            if (args == null) {
                return PojoUtils.realize(genericService.$invoke(method.getName(), new String[0], new Object[0]), method.getReturnType());
            }
            Object[] newArgs = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                newArgs[i] = PojoUtils.generalize(args[i]);
            }
            return PojoUtils.realize(genericService.$invoke(method.getName(), Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new), newArgs), method.getReturnType());
        });
        testAll(demoServiceNew);
    }

    private static void testBeanGeneric(DemoService2 demoService2) {
        DemoService2 demoServiceNew = (DemoService2) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{DemoService2.class}, (proxy, method, args) -> {
            GenericService genericService = (GenericService) demoService2;
            if (args == null) {
                Object result = genericService.$invoke(method.getName(), new String[0], new Object[0]);
                return JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) result);
            }
            Object[] newArgs = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                newArgs[i] = JavaBeanSerializeUtil.serialize(args[i]);
            }
            Object result = genericService.$invoke(method.getName(), Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new), newArgs);
            return JavaBeanSerializeUtil.deserialize((JavaBeanDescriptor) result);
        });
        testAll(demoServiceNew);
    }

    private static void testAll(DemoService2 demoService2) {
        request(demoService2);
        returnUser(demoService2);
        sendUser(demoService2);
        requestUser(demoService2);

        returnUserGeneric(demoService2);
        sendUserGeneric(demoService2);
        requestUserGeneric(demoService2);

        returnNotSerializable(demoService2);
        sendNotSerializable(demoService2);
        requestNotSerializable(demoService2);

        returnOtherPackage(demoService2);
        sendOtherPackage(demoService2);
        requestOtherPackage(demoService2);

        returnOtherNotSerializable(demoService2);
        sendOtherNotSerializable(demoService2);
        requestOtherNotSerializable(demoService2);
    }


    private static void requestOtherNotSerializable(DemoService2 demoService2) {
        io.dubbo.test2.NotSerializable notSerializable = new io.dubbo.test2.NotSerializable();
        notSerializable.setName("sendNotSerializable");

        try {
            Object o = demoService2.requestOtherPackageNotSerializable(notSerializable);
            Assert.assertTrue(o instanceof Map);
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void sendOtherNotSerializable(DemoService2 demoService2) {
        io.dubbo.test2.NotSerializable notSerializable = new io.dubbo.test2.NotSerializable();
        notSerializable.setName("sendNotSerializable");

        try {
            demoService2.sendOtherPackageNotSerializable(notSerializable);
            Assert.fail();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void returnOtherNotSerializable(DemoService2 demoService2) {
        try {
            demoService2.returnOtherPackageNotSerializable();
            Assert.fail();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void requestOtherPackage(DemoService2 demoService2) {
        OthersSerializable othersSerializable = new OthersSerializable();
        othersSerializable.setName("sendNotSerializable");

        try {
            Object o = demoService2.requestOtherPackage(othersSerializable);
            Assert.assertTrue(o instanceof Map);
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void sendOtherPackage(DemoService2 demoService2) {
        OthersSerializable othersSerializable = new OthersSerializable();
        othersSerializable.setName("sendNotSerializable");

        try {
            demoService2.sendOtherPackage(othersSerializable);
            Assert.fail();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void returnOtherPackage(DemoService2 demoService2) {
        try {
            Object o = demoService2.returnOtherPackage();
            Assert.assertTrue(o instanceof Map);
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void requestNotSerializable(DemoService2 demoService2) {
        NotSerializable notSerializable = new NotSerializable();
        notSerializable.setName("sendNotSerializable");

        try {
            demoService2.requestNotSerializable(notSerializable);
            Assert.fail();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void sendNotSerializable(DemoService2 demoService2) {
        NotSerializable notSerializable = new NotSerializable();
        notSerializable.setName("sendNotSerializable");

        try {
            demoService2.sendNotSerializable(notSerializable);
            Assert.fail();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void returnNotSerializable(DemoService2 demoService2) {
        try {
            demoService2.returnNotSerializable();
            Assert.fail();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }

    private static void requestUserGeneric(DemoService2 demoService2) {
        User2 user = new User2();
        user.setName("requestUserGeneric");
        Parent parent = new Parent();
        parent.setName("requestUserGeneric");
        user.setParent(parent);
        Assert.assertEquals(user, demoService2.requestUserGeneric(user));
    }

    private static void sendUserGeneric(DemoService2 demoService2) {
        User2 user = new User2();
        user.setName("sendUserGeneric");
        Parent parent = new Parent();
        parent.setName("sendUserGeneric");
        user.setParent(parent);
        try {
            demoService2.sendUserGeneric(user);
        } catch (Throwable t) {
            Assert.fail(t.getMessage());
        }
    }

    private static void returnUserGeneric(DemoService2 demoService2) {
        User user = demoService2.returnUserGeneric();
        Assert.assertTrue(user instanceof User2);
        Assert.assertEquals("returnUserGeneric", user.getName());
        Assert.assertEquals("returnUserGeneric", user.getParent().getName());
    }

    private static void requestUser(DemoService2 demoService2) {
        User user = new User();
        user.setName("requestUser");
        Parent parent = new Parent();
        parent.setName("requestUser");
        user.setParent(parent);
        Assert.assertEquals(user, demoService2.requestUser(user));
    }

    private static void sendUser(DemoService2 demoService2) {
        User user = new User();
        user.setName("sendUser");
        Parent parent = new Parent();
        parent.setName("sendUser");
        user.setParent(parent);
        try {
            demoService2.sendUser(user);
        } catch (Throwable t) {
            Assert.fail(t.getMessage());
        }
    }

    private static void returnUser(DemoService2 demoService2) {
        User user = demoService2.returnUser();
        Assert.assertEquals("returnUser", user.getName());
        Assert.assertEquals("returnUser", user.getParent().getName());
    }

    private static void request(DemoService2 demoService2) {
        User user = new User();
        user.setName("request");
        Parent parent = new Parent();
        parent.setName("parent");
        user.setParent(parent);
        Assert.assertEquals(user, demoService2.request(user));
    }
}
