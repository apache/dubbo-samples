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

package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import org.junit.BeforeClass;

public class TriClientTest extends BaseClientTest {

    @BeforeClass
    public static void init() {
        ReferenceConfig<Greeter> ref = new ReferenceConfig<>();
        ref.setInterface(Greeter.class);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setTimeout(10000);
        ref.setRetries(0);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(
            TriClientTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        bootstrap.application(applicationConfig)
            .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
            .reference(ref)
            .start();

        delegate = ref.get();
        appDubboBootstrap = bootstrap;
    }
}
