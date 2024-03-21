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

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes =  {DubboAutoConfiguration.class})
@RunWith(SpringRunner.class)
public class DemoServiceIT {
    @DubboReference(id = "demoServiceFromNormal",group = "normal")
    private DemoService demoServiceFromNormal;

    @DubboReference(id = "demoServiceFromService",group = "service")
    private DemoService demoServiceFromService;

    @DubboReference(id = "demoServiceFromDual",group = "dual")
    private DemoService demoServiceFromDual;

    @Before
    public void setUp() {
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

        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));
    }

    public void testApplication() throws InterruptedException {
        UpgradeUtil.writeApplicationFirstRule(100);
        checkIfNotified();

        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));


        UpgradeUtil.writeApplicationFirstRule(50);
        checkIfNotified();

        int serviceCount = 0;
        for (int i = 0; i < 100; i++) {
            if (demoServiceFromDual.sayHello("client").contains("registry-type: dual")) {
                serviceCount += 1;
            }
        }
        Assert.assertTrue(serviceCount <= 100);
    }

    public void testApplicationForce() throws InterruptedException {
        UpgradeUtil.writeForceApplicationRule();
        checkIfNotified();

        try {
            demoServiceFromNormal.sayHello("client");
        } catch (RpcException ignore) {

        }
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));
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
