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
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.SslConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;

import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

import static io.grpc.examples.helloworld.DubboGreeterGrpc.IGreeter;

public class SslBasicConsumer {

    private static final String ROOT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        SslConfig sslConfig = new SslConfig();
        if (args.length > 0) {
            if (args.length != 1 && args.length != 3) {
                System.out.println(
                        "USAGE: BasicConsumer [trustCertCollectionFilePath [certChainFilePath privateKeyFilePath]]\n " +
                                "Specify 'certChainFilePath' and 'privateKeyFilePath' only if you need Mutual TLS.");
                System.exit(0);
            }

            switch (args.length) {
                case 1:
                    sslConfig.setClientTrustCertCollectionPath(args[0]);
                    break;
                case 3:
                    sslConfig.setClientTrustCertCollectionPath(args[0]);
                    sslConfig.setClientKeyCertChainPath(args[1]);
                    sslConfig.setClientPrivateKeyPath(args[2]);
            }
        }

        DubboBootstrap bootstrap = DubboBootstrap.getInstance()
                .application(new ApplicationConfig("first-dubbo-consumer"))
                .registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .ssl(sslConfig);

        ReferenceConfig<IGreeter> reference = new ReferenceConfig<>();
        reference.setInterface(IGreeter.class);

        bootstrap.reference(reference);

        bootstrap.start();

        IGreeter service = bootstrap.getCache().get(reference);
        HelloReply helloReply = service.sayHello(HelloRequest.newBuilder().setName("dubbo").build());
        System.out.println(helloReply.getMessage());
    }
}
