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

package org.apache.dubbo.samples.governance;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.registry.support.AbstractRegistryFactory;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.samples.governance.api.DemoService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/dubbo-demo-consumer.xml"})
public class DemoServiceIT {
    @Autowired
    private DemoService demoService;

    @BeforeClass
    public static void setUp() throws Exception {
        ZKTools.initClient();
    }

    @Test
    public void test20880_interface() throws Exception {
        UpgradeUtil.writeForceInterfaceRule();
        checkIfNotified();
        ZKTools.generateAppLevelOverride(100, 0);
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            String result = demoService.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20880"));
        }
    }

    @Test
    public void test20880_application() throws Exception {
        UpgradeUtil.writeForceApplicationRule();
        checkIfNotified();
        ZKTools.generateAppLevelOverride(100, 0);
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            String result = demoService.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20880"));
        }
    }

    @Test
    public void test20881_interface() throws Exception {
        UpgradeUtil.writeForceInterfaceRule();
        checkIfNotified();
        ZKTools.generateAppLevelOverride(0, 100);
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            String result = demoService.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20881"));
        }
    }

    @Test
    public void test20881_application() throws Exception {
        UpgradeUtil.writeForceApplicationRule();
        checkIfNotified();
        ZKTools.generateAppLevelOverride(0, 100);
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            String result = demoService.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20881"));
        }
    }

    private void checkIfNotified() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            if (FrameworkStatusReporterImpl.getReport().size() == 1) {
                FrameworkStatusReporterImpl.clearReport();
                return;
            }
            Thread.sleep(100);
        }
    }
}
