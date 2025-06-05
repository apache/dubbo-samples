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

import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class DemoServiceConfigIT {
    private static ApplicationModel applicationModel;

    @After
    public void teardown() {
        System.clearProperty("dubbo.application.service-discovery.migration");
    }

    @Before
    public void setup() {
        applicationModel = FrameworkModel.defaultModel().newApplication();
        ApplicationConfig applicationConfig = new ApplicationConfig(applicationModel);
        // Set a different name to avoid affecting DemoServiceIT.
        applicationConfig.setName("dubbo-servicediscovery-migration-consumer-1");

        RegistryConfig registryConfig = new RegistryConfig(applicationModel);
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress(System.getProperty("zookeeper.address", "127.0.0.1"));
        registryConfig.setPort(Integer.parseInt(System.getProperty("zookeeper.port", "2181")));

        applicationModel.getApplicationConfigManager().setApplication(applicationConfig);
        applicationModel.getApplicationConfigManager().addRegistry(registryConfig);
        applicationModel.getDeployer().start();
        UpgradeUtil.clearRule(applicationModel);
    }

    private DemoService buildNormal(String mode) {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>(applicationModel.newModule());
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setGroup("normal");
        referenceConfig.setCheck(false);
        if (StringUtils.isNotEmpty(mode)) {
            referenceConfig.setParameters(new HashMap<>());
            referenceConfig.getParameters().put("migration.step", mode);
        }
        return referenceConfig.get();
    }

    private DemoService buildDual(String mode) {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>(applicationModel.newModule());
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setGroup("dual");
        referenceConfig.setCheck(false);
        if (StringUtils.isNotEmpty(mode)) {
            referenceConfig.setParameters(new HashMap<>());
            referenceConfig.getParameters().put("migration.step", mode);
        }
        return referenceConfig.get();
    }

    private DemoService buildService(String mode) {
        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>(applicationModel.newModule());
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setGroup("service");
        referenceConfig.setCheck(false);
        if (StringUtils.isNotEmpty(mode)) {
            referenceConfig.setParameters(new HashMap<>());
            referenceConfig.getParameters().put("migration.step", mode);
        }
        return referenceConfig.get();
    }

    @Test
    public void testApplicationConfig1() throws InterruptedException {
        // Application level config (default:APPLCIATION_FIRST)
        System.clearProperty("dubbo.application.service-discovery.migration");

        DemoService demoServiceFromNormal = buildNormal(null);
        DemoService demoServiceFromService = buildService(null);
        DemoService demoServiceFromDual = buildDual(null);

        // dual register => might return service and normal
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        // service-discovery register => should return service
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));

        // interface register => should return normal
        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
    }

    @Test
    public void testApplicationConfig2() throws InterruptedException {
        // Application level config (APPLICATION_FIRST)
        System.setProperty("dubbo.application.service-discovery.migration", "APPLICATION_FIRST");

        DemoService demoServiceFromNormal = buildNormal(null);
        DemoService demoServiceFromService = buildService(null);
        DemoService demoServiceFromDual = buildDual(null);

        // dual register => might return service and normal
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));


        // service-discovery register => should return service
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));

        // interface register => should return normal
        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
    }

    @Test
    public void testApplicationConfig3() throws InterruptedException {
        // Application level config (FORCE_INTERFACE)
        System.setProperty("dubbo.application.service-discovery.migration", "FORCE_INTERFACE");

        DemoService demoServiceFromNormal = buildNormal(null);
        DemoService demoServiceFromService = buildService(null);
        DemoService demoServiceFromDual = buildDual(null);

        // dual register => should return normal
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        // interface register => should return normal
        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));

        try {
            // service-discovery register => should throw address not found exception
            demoServiceFromService.sayHello("client");
        } catch (RpcException ignore) {

        }
    }

    @Test
    public void testApplicationConfig4() throws InterruptedException {
        // Application level config (FORCE_APPLICATION)
        System.setProperty("dubbo.application.service-discovery.migration", "FORCE_APPLICATION");

        DemoService demoServiceFromNormal = buildNormal(null);
        DemoService demoServiceFromService = buildService(null);
        DemoService demoServiceFromDual = buildDual(null);

        // dual register => should return service
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        // service-discovery register => should throw address not found exception
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));

        try {
            // interface register => should return normal
            demoServiceFromNormal.sayHello("client");
        } catch (RpcException ignore) {

        }
    }

    @Test
    public void testReferenceConfig1() throws InterruptedException {
        // Reference level config (default:APPLCIATION_FIRST)
        DemoService demoServiceFromNormal = buildNormal(null);
        DemoService demoServiceFromService = buildService(null);
        DemoService demoServiceFromDual = buildDual(null);

        // dual register => might return service and normal
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        // service-discovery register => should return service
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));

        // interface register => should return normal
        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
    }

    @Test
    public void testReferenceConfig2() throws InterruptedException {
        // Reference level config (APPLICATION_FIRST)
        DemoService demoServiceFromNormal = buildNormal("APPLICATION_FIRST");
        DemoService demoServiceFromService = buildService("APPLICATION_FIRST");
        DemoService demoServiceFromDual = buildDual("APPLICATION_FIRST");

        // dual register => might return service and normal
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));


        // service-discovery register => should return service
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));

        // interface register => should return normal
        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));
    }

    @Test
    public void testReferenceConfig3() throws InterruptedException {
        // Reference level config (FORCE_INTERFACE)
        DemoService demoServiceFromNormal = buildNormal("FORCE_INTERFACE");
        DemoService demoServiceFromService = buildService("FORCE_INTERFACE");
        DemoService demoServiceFromDual = buildDual("FORCE_INTERFACE");

        // dual register => should return normal
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        // interface register => should return normal
        Assert.assertTrue(demoServiceFromNormal.sayHello("client").contains("registry-type: normal"));

        try {
            // service-discovery register => should throw address not found exception
            demoServiceFromService.sayHello("client");
        } catch (RpcException ignore) {

        }
    }

    @Test
    public void testReferenceConfig4() throws InterruptedException {
        // Reference level config (FORCE_APPLICATION)
        DemoService demoServiceFromNormal = buildNormal("FORCE_APPLICATION");
        DemoService demoServiceFromService = buildService("FORCE_APPLICATION");
        DemoService demoServiceFromDual = buildDual("FORCE_APPLICATION");

        // dual register => should return dual
        Assert.assertTrue(demoServiceFromDual.sayHello("client").contains("registry-type: dual"));

        // service-discovery register => should throw address not found exception
        Assert.assertTrue(demoServiceFromService.sayHello("client").contains("registry-type: service"));

        try {
            // interface register => should return normal
            demoServiceFromNormal.sayHello("client");
        } catch (RpcException ignore) {

        }
    }
}
