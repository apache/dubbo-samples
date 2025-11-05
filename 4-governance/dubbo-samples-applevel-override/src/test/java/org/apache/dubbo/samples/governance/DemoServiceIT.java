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

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.client.migration.MigrationInvoker;
import org.apache.dubbo.registry.client.migration.model.MigrationStep;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.cluster.ClusterInvoker;
import org.apache.dubbo.rpc.model.ConsumerModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.samples.governance.api.DemoService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.awaitility.Awaitility.await;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/dubbo-demo-consumer.xml"})
public class DemoServiceIT {
    @Autowired
    private DemoService demoService;
    @Autowired
    private DemoService demoService2;

    @BeforeClass
    public static void setUp() throws Exception {
        ZKTools.initClient();
    }

    @Test
    public void test20880_interface() throws Exception {
        UpgradeUtil.writeForceInterfaceRule();
        await().until(() -> checkIfNotified(MigrationStep.FORCE_INTERFACE));
        ZKTools.generateAppLevelOverride(100, 0);
        await().until(() -> checkWeight(100, 0));
        for (int i = 0; i < 10; i++) {
            String result = demoService.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20880"));
        }
    }

    @Test
    public void test20880_application() throws Exception {
        UpgradeUtil.writeForceApplicationRule();
        await().until(() -> checkIfNotified(MigrationStep.FORCE_APPLICATION));
        ZKTools.generateAppLevelOverride(100, 0);
        await().until(() -> checkWeight(100, 0));
        for (int i = 0; i < 10; i++) {
            String result = demoService.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20880"));
        }
    }

    @Test
    public void test20881_interface() throws Exception {
        UpgradeUtil.writeForceInterfaceRule();
        await().until(() -> checkIfNotified(MigrationStep.FORCE_INTERFACE));
        ZKTools.generateAppLevelOverride(0, 100);
        await().until(() -> checkWeight(0, 100));
        for (int i = 0; i < 10; i++) {
            String result = demoService.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20881"));
        }
    }

    @Test
    public void test20881_application() throws Exception {
        UpgradeUtil.writeForceApplicationRule();
        await().until(() -> checkIfNotified(MigrationStep.FORCE_APPLICATION));
        ZKTools.generateAppLevelOverride(0, 100);
        await().until(() -> checkWeight(0, 100));
        for (int i = 0; i < 10; i++) {
            String result = demoService.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20881"));
        }
    }

    @Test
    public void test20880_interface2() throws Exception {
        UpgradeUtil.writeForceInterfaceRule();
        await().until(() -> checkIfNotified(MigrationStep.FORCE_INTERFACE));
        ZKTools.generateAppLevelOverride(100, 0);
        await().until(() -> checkWeight(100, 0));
        for (int i = 0; i < 10; i++) {
            String result = demoService2.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20880"));
        }
    }

    @Test
    public void test20880_application2() throws Exception {
        UpgradeUtil.writeForceApplicationRule();
        await().until(() -> checkIfNotified(MigrationStep.FORCE_APPLICATION));
        ZKTools.generateAppLevelOverride(100, 0);
        await().until(() -> checkWeight(100, 0));
        for (int i = 0; i < 10; i++) {
            String result = demoService2.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20880"));
        }
    }

    @Test
    public void test20881_interface2() throws Exception {
        UpgradeUtil.writeForceInterfaceRule();
        await().until(() -> checkIfNotified(MigrationStep.FORCE_INTERFACE));
        ZKTools.generateAppLevelOverride(0, 100);
        await().until(() -> checkWeight(0, 100));
        for (int i = 0; i < 10; i++) {
            String result = demoService2.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20881"));
        }
    }

    @Test
    public void test20881_application2() throws Exception {
        UpgradeUtil.writeForceApplicationRule();
        await().until(() -> checkIfNotified(MigrationStep.FORCE_APPLICATION));
        ZKTools.generateAppLevelOverride(0, 100);
        await().until(() -> checkWeight(0, 100));
        for (int i = 0; i < 10; i++) {
            String result = demoService2.sayHello("world");
            System.out.println(result);
            Assert.assertTrue(result.contains("20881"));
        }
    }

    private boolean checkIfNotified(MigrationStep expectedStep) throws InterruptedException {
        return FrameworkModel.defaultModel().getServiceRepository()
                .allConsumerModels()
                .stream()
                .map(ConsumerModel::getServiceMetadata)
                .map(s -> s.getAttribute(CommonConstants.CURRENT_CLUSTER_INVOKER_KEY))
                .filter(Objects::nonNull)
                .map(m -> (Map<Registry, MigrationInvoker<?>>) m)
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .map(Map.Entry::getValue)
                .map(MigrationInvoker::getMigrationStep)
                .allMatch(expectedStep::equals);
    }

    private boolean checkWeight(int weight1, int weight2) throws InterruptedException {
        AtomicBoolean match = new AtomicBoolean(true);
        FrameworkModel.defaultModel().getServiceRepository()
                .allConsumerModels()
                .stream()
                .map(ConsumerModel::getServiceMetadata)
                .map(s -> s.getAttribute(CommonConstants.CURRENT_CLUSTER_INVOKER_KEY))
                .filter(Objects::nonNull)
                .map(m -> (Map<Registry, MigrationInvoker<?>>) m)
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .map(Map.Entry::getValue)
                .map(MigrationInvoker::getCurrentAvailableInvoker)
                .map(ClusterInvoker::getDirectory)
                .forEach(directory -> {
                    RpcContext.getServiceContext().setConsumerUrl(directory.getConsumerUrl());
                    if (!directory.getAllInvokers()
                            .stream()
                            .map(Invoker::getUrl)
                            .allMatch(url -> {
                                if (url.getPort() == 20880) {
                                    return weight1 == url.getServiceParameter(DemoService.class.getName(), "weight", -1);
                                } else {
                                    return weight2 == url.getParameter("weight", -1);
                                }
                            })) {
                        match.set(false);
                    }
                    if (directory.getAllInvokers().size() != 2) {
                        match.set(false);
                    }
                });
        return match.get();
    }
}
