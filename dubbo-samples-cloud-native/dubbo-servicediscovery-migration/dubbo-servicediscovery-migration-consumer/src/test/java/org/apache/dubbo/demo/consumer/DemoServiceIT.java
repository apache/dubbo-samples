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
package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.rpc.RpcException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/dubbo-consumer.xml")
public class DemoServiceIT {
    @Autowired
    @Qualifier("demoServiceFromNormal")
    private DemoService demoServiceFromNormal;

    @Autowired
    @Qualifier("demoServiceFromService")
    private DemoService demoServiceFromService;

    @Autowired
    @Qualifier("demoServiceFromDual")
    private DemoService demoServiceFromDual;

    @Before
    public void setUp() throws Exception {
        FrameworkStatusReporterImpl.clearReport();
    }

    @Test
    public void test() throws InterruptedException {
        FrameworkStatusReporterImpl.clearReport();
        // FORCE_INTERFACE --> APPLICATION_FIRST
        testInterface();
        testApplication();

        // APPLICATION_FIRST --> FORCE_APPLICATION
        testApplicationForce();

        // FORCE_APPLICATION --> APPLICATION_FIRST
        testApplication();

        // APPLICATION_FIRST --> FORCE_INTERFACE
        testInterface();

        // FORCE_INTERFACE --> FORCE_APPLICATION
        testApplicationForce();

        // FORCE_APPLICATION --> FORCE_INTERFACE
        testInterface();
    }

    public void testInterface() throws InterruptedException {
        UpgradeUtil.writeForceInterfaceRule();
        checkIfNotified();

        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
        try {
            demoServiceFromService.sayHello("client");
            Assert.fail();
        } catch (RpcException ignore) {

        }
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: normal"));
    }

    public void testApplication() throws InterruptedException {
        UpgradeUtil.writeApplicationFirstRule(100);
        checkIfNotified();

        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: service"));


        UpgradeUtil.writeApplicationFirstRule(50);
        checkIfNotified();

        int serviceCount = 0;
        for (int i = 0; i < 100; i++) {
            if (demoServiceFromDual.sayHello("client").contains("registry-type: service")) {
                serviceCount += 1;
            }
        }
        Assert.assertTrue(serviceCount < 100);
    }

    public void testApplicationForce() throws InterruptedException {
        UpgradeUtil.writeForceApplicationRule();
        checkIfNotified();

        try {
            demoServiceFromNormal.sayHello("client");
            Assert.fail();
        } catch (RpcException ignore) {

        }
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: service"));
    }

    private void checkIfNotified() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            if (FrameworkStatusReporterImpl.getReport().size() == 3) {
                FrameworkStatusReporterImpl.clearReport();
                return;
            }
            Thread.sleep(100);
        }
    }
}
