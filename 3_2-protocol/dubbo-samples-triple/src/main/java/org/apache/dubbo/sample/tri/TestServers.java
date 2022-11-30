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
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.interop.client.GrpcServer;
import org.apache.dubbo.sample.tri.pojo.PojoGreeterImpl;
import org.apache.dubbo.sample.tri.stub.GreeterImpl;
import org.apache.dubbo.sample.tri.util.EmbeddedZooKeeper;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import java.io.IOException;


/**
 * for Integration testing
 */
public class TestServers {

    public static void main(String[] args) throws IOException, InterruptedException {
        new EmbeddedZooKeeper(TriSampleConstants.ZK_PORT, false).start();

        // grpc
        GrpcServer server = new GrpcServer(TriSampleConstants.GRPC_SERVER_PORT);
        server.initialize();
        server.start();

        ServiceConfig<Greeter> pbService = new ServiceConfig<>();
        pbService.setInterface(Greeter.class);
        GreeterImpl greeterImpl = new GreeterImpl("tri-stub");
        pbService.setRef(greeterImpl);


        ServiceConfig<PojoGreeter> wrapService = new ServiceConfig<>();
        wrapService.setInterface(PojoGreeter.class);
        wrapService.setRef(new PojoGreeterImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-provider"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, TriSampleConstants.SERVER_PORT))
                .service(pbService)
                .service(wrapService)
                .start()
                .await();
    }
}
