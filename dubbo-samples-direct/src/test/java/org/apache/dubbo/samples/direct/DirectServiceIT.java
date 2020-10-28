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

package org.apache.dubbo.samples.direct;

import junit.framework.TestCase;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.samples.direct.api.DirectService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/dubbo-direct-consumer.xml"})
public class DirectServiceIT {
    private static String providerAddress = System.getProperty("target.address", "localhost");

    @Autowired
    private DirectService directService;

    @Test
    public void testXml() throws Exception {
        Assert.assertTrue(directService.sayHello("dubbo").startsWith("Hello dubbo"));
    }

    @Test
    public void testGeneric() throws Exception {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("direct-consumer");
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://" + providerAddress + ":20880/" + DirectService.class.getName());
        reference.setVersion("1.0.0-daily");
        reference.setGroup("test");
        reference.setGeneric(true);
        reference.setApplication(application);
        reference.setInterface(DirectService.class.getName());
        GenericService genericService = reference.get();
        Object obj = genericService.$invoke("sayHello", new String[]{String.class.getName()}, new Object[]{ "generic" });
        String str = (String) obj;
        TestCase.assertTrue(str.startsWith("Hello generic"));
    }

    @Test
    public void testApi() throws Exception {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("direct-consumer");
        ReferenceConfig<DirectService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://" + providerAddress + ":20880/" + DirectService.class.getName());
        reference.setVersion("1.0.0-daily");
        reference.setGroup("test");
        reference.setApplication(application);
        reference.setInterface(DirectService.class.getName());
        DirectService service = reference.get();
        String result = service.sayHello("api");
        TestCase.assertTrue(result.startsWith("Hello api"));
    }
}
