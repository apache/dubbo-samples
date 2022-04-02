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

package org.apache.dubbo.sample.tri.pojo;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.context.Lifecycle;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.util.EmbeddedZooKeeper;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import java.io.IOException;

/**
 * A sample for using tri protocol with manual java interface and POJO
 */
public class TriPojoServer implements Lifecycle {
    private final int port;

    public TriPojoServer(int serverPort) {
        this.port = serverPort;
    }

    public static void main(String[] args) throws IOException {
        TriPojoServer server = new TriPojoServer(TriSampleConstants.SERVER_PORT);
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
        ServiceConfig<PojoGreeter> service = new ServiceConfig<>();
        service.setInterface(PojoGreeter.class);
        service.setRef(new PojoGreeterImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("tri-stub-server"))
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
