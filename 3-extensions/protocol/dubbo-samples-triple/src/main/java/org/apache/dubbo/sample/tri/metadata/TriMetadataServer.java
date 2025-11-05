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

package org.apache.dubbo.sample.tri.metadata;


import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.context.Lifecycle;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.Greeter;
import org.apache.dubbo.sample.tri.util.EmbeddedZooKeeper;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class TriMetadataServer implements Lifecycle {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriMetadataServer.class);
    private final int port;

    public TriMetadataServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        TriMetadataServer server = new TriMetadataServer(TriSampleConstants.SERVER_PORT);
        server.initialize();
        server.start();
        System.in.read();
    }

    @Override
    public void initialize() throws IllegalStateException {
        new EmbeddedZooKeeper(TriSampleConstants.ZK_PORT, false).start();
    }

    @Override
    public void start() throws IllegalStateException {
        ServiceConfig<Greeter> service = new ServiceConfig<>();
        service.setInterface(Greeter.class);
        service.setRef(new MetadataGreeterImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();

        ApplicationConfig applicationConfig = new ApplicationConfig("tri-metadata-server");
        applicationConfig.setMetadataServiceProtocol(CommonConstants.TRIPLE);

        bootstrap.application(applicationConfig)
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, port))
                .service(service)
                .start();
    }

    @Override
    public void destroy() throws IllegalStateException {
        DubboBootstrap.getInstance().stop();
    }
}
