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

import org.apache.dubbo.common.utils.PojoUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.samples.api.GreetingsService;
import org.apache.dubbo.samples.api.SerializationTypeWrap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GreetingServiceIT {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    @Test
    public void test1() {
        ReferenceConfig<GreetingsService> referenceHessian2 = new ReferenceConfig<>();
        referenceHessian2.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceHessian2.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        referenceHessian2.setInterface(GreetingsService.class);
        referenceHessian2.setVersion("hessian2");
        GreetingsService hessian2Service = referenceHessian2.get();

        SerializationTypeWrap serializationTypeWrap = hessian2Service.echo(new SerializationTypeWrap());
        Assertions.assertEquals("x-application/hessian2", serializationTypeWrap.getContentType());
        Assertions.assertEquals("x-application/hessian2", serializationTypeWrap.getParams());

        ReferenceConfig<GreetingsService> referenceFastjson2 = new ReferenceConfig<>();
        referenceFastjson2.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceFastjson2.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        referenceFastjson2.setInterface(GreetingsService.class);
        referenceFastjson2.setVersion("fastjson2");
        GreetingsService fastjson2Service = referenceFastjson2.get();

        serializationTypeWrap = fastjson2Service.echo(new SerializationTypeWrap());
        Assertions.assertEquals("text/jsonb", serializationTypeWrap.getContentType());
        Assertions.assertEquals("text/jsonb", serializationTypeWrap.getParams());

        FrameworkModel.destroyAll();
    }

    @Test
    public void test2() {
        ReferenceConfig<GreetingsService> referenceFastjson2 = new ReferenceConfig<>();
        referenceFastjson2.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceFastjson2.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        referenceFastjson2.setInterface(GreetingsService.class);
        referenceFastjson2.setVersion("fastjson2");
        GreetingsService fastjson2Service = referenceFastjson2.get();

        SerializationTypeWrap serializationTypeWrap = fastjson2Service.echo(new SerializationTypeWrap());
        Assertions.assertEquals("text/jsonb", serializationTypeWrap.getContentType());
        Assertions.assertEquals("text/jsonb", serializationTypeWrap.getParams());

        ReferenceConfig<GreetingsService> referenceHessian2 = new ReferenceConfig<>();
        referenceHessian2.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceHessian2.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        referenceHessian2.setInterface(GreetingsService.class);
        referenceHessian2.setVersion("hessian2");
        GreetingsService hessian2Service = referenceHessian2.get();

        serializationTypeWrap = hessian2Service.echo(new SerializationTypeWrap());
        Assertions.assertEquals("x-application/hessian2", serializationTypeWrap.getContentType());
        Assertions.assertEquals("x-application/hessian2", serializationTypeWrap.getParams());

        FrameworkModel.destroyAll();
    }

    @Test
    public void testGeneric1() {
        ReferenceConfig<GenericService> referenceHessian2 = new ReferenceConfig<>();
        referenceHessian2.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceHessian2.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        referenceHessian2.setInterface(GreetingsService.class);
        referenceHessian2.setGeneric("true");
        referenceHessian2.setVersion("hessian2");
        GenericService hessian2Service = referenceHessian2.get();

        Object res = hessian2Service.$invoke("echo",
                new String[]{SerializationTypeWrap.class.getName()},
                new Object[]{PojoUtils.generalize(new SerializationTypeWrap())});
        SerializationTypeWrap serializationTypeWrap =
                (SerializationTypeWrap) PojoUtils.realize(res, SerializationTypeWrap.class);
        Assertions.assertEquals("x-application/hessian2", serializationTypeWrap.getContentType());
        Assertions.assertEquals("x-application/hessian2", serializationTypeWrap.getParams());

        ReferenceConfig<GenericService> referenceFastjson2 = new ReferenceConfig<>();
        referenceFastjson2.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceFastjson2.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        referenceFastjson2.setInterface(GreetingsService.class);
        referenceFastjson2.setVersion("fastjson2");
        referenceFastjson2.setGeneric("true");
        GenericService fastjson2Service = referenceFastjson2.get();

        res = fastjson2Service.$invoke("echo",
                new String[]{SerializationTypeWrap.class.getName()},
                new Object[]{PojoUtils.generalize(new SerializationTypeWrap())});
        serializationTypeWrap =
                (SerializationTypeWrap) PojoUtils.realize(res, SerializationTypeWrap.class);
        Assertions.assertEquals("text/jsonb", serializationTypeWrap.getContentType());
        Assertions.assertEquals("text/jsonb", serializationTypeWrap.getParams());

        FrameworkModel.destroyAll();
    }

    @Test
    public void testGeneric2() {
        ReferenceConfig<GenericService> referenceFastjson2 = new ReferenceConfig<>();
        referenceFastjson2.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceFastjson2.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        referenceFastjson2.setInterface(GreetingsService.class);
        referenceFastjson2.setVersion("fastjson2");
        referenceFastjson2.setGeneric("true");
        GenericService fastjson2Service = referenceFastjson2.get();

        Object res = fastjson2Service.$invoke("echo",
                new String[]{SerializationTypeWrap.class.getName()},
                new Object[]{PojoUtils.generalize(new SerializationTypeWrap())});
        SerializationTypeWrap serializationTypeWrap =
                (SerializationTypeWrap) PojoUtils.realize(res, SerializationTypeWrap.class);
        Assertions.assertEquals("text/jsonb", serializationTypeWrap.getContentType());
        Assertions.assertEquals("text/jsonb", serializationTypeWrap.getParams());

        ReferenceConfig<GenericService> referenceHessian2 = new ReferenceConfig<>();
        referenceHessian2.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceHessian2.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        referenceHessian2.setInterface(GreetingsService.class);
        referenceHessian2.setGeneric("true");
        referenceHessian2.setVersion("hessian2");
        GenericService hessian2Service = referenceHessian2.get();

        res = hessian2Service.$invoke("echo",
                new String[]{SerializationTypeWrap.class.getName()},
                new Object[]{PojoUtils.generalize(new SerializationTypeWrap())});
        serializationTypeWrap =
                (SerializationTypeWrap) PojoUtils.realize(res, SerializationTypeWrap.class);
        Assertions.assertEquals("x-application/hessian2", serializationTypeWrap.getContentType());
        Assertions.assertEquals("x-application/hessian2", serializationTypeWrap.getParams());

        FrameworkModel.destroyAll();
    }
}
