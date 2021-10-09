/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.dubbo.sample.tri.grpc;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.Constants;
import org.apache.dubbo.sample.tri.BasePbConsumerTest;
import org.apache.dubbo.sample.tri.PbGreeter;
import org.apache.dubbo.sample.tri.TriSampleConstants;
import org.apache.dubbo.sample.tri.service.PbGreeterManual;
import org.junit.BeforeClass;

public class TriGrpcCompressionConsumerTest extends BasePbConsumerTest {

    /**
     * @see Constants#COMPRESSOR_KEY
     * @since 3.0.4-SNAPSHOT
     */
    @SuppressWarnings("JavadocReference")
    private static final String COMPRESSOR_KEY = "dubbo.rpc.tri.compressor";

    @BeforeClass
    public static void init() {
        ReferenceConfig<PbGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(PbGreeter.class);
        ref.setCheck(false);
        ref.setUrl(TriSampleConstants.DEFAULT_ADDRESS);
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setLazy(true);
        ref.setTimeout(3000);

        ReferenceConfig<PbGreeterManual> ref2 = new ReferenceConfig<>();
        ref2.setInterface(PbGreeterManual.class);
        ref2.setCheck(false);
        ref2.setUrl(TriSampleConstants.DEFAULT_ADDRESS);
        ref2.setProtocol(CommonConstants.TRIPLE);
        ref2.setLazy(true);
        ref2.setTimeout(3000);

        System.getProperties().put(COMPRESSOR_KEY, "gzip");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(TriGrpcCompressionConsumerTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        bootstrap.application(applicationConfig)
            .reference(ref)
            .reference(ref2)
            .start();
        delegate = ref.get();
        delegateManual = ref2.get();
        appDubboBootstrap=bootstrap;
    }

}
