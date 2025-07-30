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
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ApplicationModel applicationModel;

    @Test
    public void test() throws InterruptedException {
        UpgradeUtil.clearRule(applicationModel);

        // FORCE_INTERFACE
        testInterface(2, false);

        // FORCE_INTERFACE --> APPLICATION_FIRST
        testApplication(2, true);

        // APPLICATION_FIRST --> FORCE_APPLICATION
        testApplicationForce(3, true);

        // FORCE_APPLICATION --> APPLICATION_FIRST
        testApplication(3, true);

        // APPLICATION_FIRST --> FORCE_INTERFACE
        testInterface(3, true);

        // FORCE_INTERFACE --> FORCE_APPLICATION
        testApplicationForce(3, true);

        // FORCE_APPLICATION --> FORCE_INTERFACE
        testInterface(3, true);
    }

    public void testInterface(int expectedMigrationReportCount, boolean waitImmediately) throws InterruptedException {
        FrameworkStatusReporterImpl.prepare("FORCE_INTERFACE", expectedMigrationReportCount);
        UpgradeUtil.writeForceInterfaceRule();
        if (waitImmediately) {
            Assert.assertTrue(FrameworkStatusReporterImpl.waitReport(5000));
        }

        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        if (!waitImmediately) {
            Assert.assertTrue(FrameworkStatusReporterImpl.waitReport(5000));
        }
    }

    public void testApplication(int expectedMigrationReportCount, boolean waitImmediately) throws InterruptedException {
        FrameworkStatusReporterImpl.prepare("APPLICATION_FIRST", expectedMigrationReportCount);
        UpgradeUtil.writeApplicationFirstRule(100);
        if (waitImmediately) {
            Assert.assertTrue(FrameworkStatusReporterImpl.waitReport(500000));
        }

        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        UpgradeUtil.writeApplicationFirstRule(50);

        int serviceCount = 0;
        for (int i = 0; i < 100; i++) {
            if (demoServiceFromDual.sayHello("client").contains("registry-type: dual")) {
                serviceCount += 1;
            }
        }
        Assert.assertTrue(serviceCount <= 100);

        if (!waitImmediately) {
            Assert.assertTrue(FrameworkStatusReporterImpl.waitReport(5000));
        }
    }

    public void testApplicationForce(int expectedMigrationReportCount, boolean waitImmediately) throws InterruptedException {
        FrameworkStatusReporterImpl.prepare("FORCE_APPLICATION", expectedMigrationReportCount);
        UpgradeUtil.writeForceApplicationRule();
        if (waitImmediately) {
            Assert.assertTrue(FrameworkStatusReporterImpl.waitReport(5000));
        }

        try {
            demoServiceFromNormal.sayHello("client");
        } catch (RpcException ignore) {

        }
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        if (!waitImmediately) {
            Assert.assertTrue(FrameworkStatusReporterImpl.waitReport(5000));
        }
    }
}
