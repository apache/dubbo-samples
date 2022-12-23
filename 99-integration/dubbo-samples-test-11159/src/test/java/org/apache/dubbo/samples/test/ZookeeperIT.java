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
package org.apache.dubbo.samples.test;

import org.apache.dubbo.common.config.ConfigurationUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MetadataReportConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import org.junit.Assert;
import org.junit.Test;

public class ZookeeperIT {
    private static final String zookeeperAddress = "zookeeper://" + System.getProperty("zookeeper.address", "127.0.0.1") + ":2181";

    @Test
    public void test() {
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());
        service.setApplication(new ApplicationConfig("dubbo-demo-api-provider"));
        service.setRegistry(new RegistryConfig(zookeeperAddress));
        service.setMetadataReportConfig(new MetadataReportConfig(zookeeperAddress));
        service.export();

        String val = ConfigurationUtils.getProperty(service.getScopeModel(), "a", "c");
        Assert.assertEquals("bad value", "b", val);
    }
}
