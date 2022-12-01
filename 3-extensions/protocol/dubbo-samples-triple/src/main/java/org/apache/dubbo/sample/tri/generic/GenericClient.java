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

package org.apache.dubbo.sample.tri.generic;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GenericClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericClient.class);

    private final GenericService generic;

    GenericClient() {
        ReferenceConfig<GenericService> ref = new ReferenceConfig<>();
        ref.setInterface(PojoGreeter.class.getName());
        ref.setProtocol(CommonConstants.TRIPLE);
        ref.setGeneric("true");
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-consumer"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref)
                .start();
        this.generic = ref.get();
    }

    public static void main(String[] args) throws IOException {
        final GenericClient consumer = new GenericClient();
        LOGGER.info("dubbo triple generic consumer started");
        consumer.greetUnary();
        System.in.read();
    }

    public void greetUnary() {
        Object resp = generic.$invoke("greet", new String[]{String.class.getName()}, new Object[]{"unary"});
        LOGGER.info("Generic call returns <-{}", resp);
    }

}