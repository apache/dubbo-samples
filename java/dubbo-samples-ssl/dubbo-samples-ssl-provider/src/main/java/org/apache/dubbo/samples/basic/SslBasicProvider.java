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

package org.apache.dubbo.samples.basic;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.SslConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.samples.basic.api.DemoService;
import org.apache.dubbo.samples.basic.impl.DemoServiceImpl;

import java.util.concurrent.CountDownLatch;

public class SslBasicProvider {

    private static final String ROOT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2181, false).start();
        // wait for embedded zookeeper start completely.
        Thread.sleep(1000);

        SslConfig sslConfig = new SslConfig();
        if (args.length > 0) {
            if (args.length < 2 || args.length > 3) {
                System.out.println(
                        "USAGE: BasicProvider certChainFilePath privateKeyFilePath " +
                                "[trustCertCollectionFilePath]\n Specify 'trustCertCollectionFilePath' only if you want " +
                                "need Mutual TLS.");
                System.exit(0);
            }


            sslConfig.setServerKeyCertChainPath(args[0]);
            sslConfig.setServerPrivateKeyPath(args[1]);
            if (args.length == 3) {
                sslConfig.setServerTrustCertCollectionPath(args[2]);
            }
        }

        ProtocolConfig protocolConfig = new ProtocolConfig("dubbo");
        protocolConfig.setSslEnabled(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("ssl-provider"))
                 .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                 .protocol(protocolConfig)
                 .ssl(sslConfig);

        ServiceConfig<DemoService> service = new ServiceConfig<>();
        service.setInterface(DemoService.class);
        service.setRef(new DemoServiceImpl());

        bootstrap.service(service);
        bootstrap.start();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
