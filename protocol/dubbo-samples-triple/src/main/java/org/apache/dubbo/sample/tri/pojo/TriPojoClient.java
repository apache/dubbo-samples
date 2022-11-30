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
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.util.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class TriPojoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriPojoClient.class);

    private final PojoGreeter delegate;
    private final String clientName = "tri-pojo";

    public TriPojoClient() {
        ReferenceConfig<PojoGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(PojoGreeter.class);
        ref.setTimeout(3000);
        ref.setProtocol(CommonConstants.TRIPLE);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("tri-pojo-client"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref)
                .start();
        this.delegate = ref.get();
    }

    public static void main(String[] args) throws IOException {
        final TriPojoClient consumer = new TriPojoClient();
        consumer.greetUnary();
        consumer.greetStream();
        consumer.greetServerStream();
        System.in.read();
    }

    public void greetUnary() {
        LOGGER.info("{} Start unary", clientName);
        String reply = delegate.greet("unary");
        LOGGER.info("{} Unary reply <-{}", clientName, reply);
    }

    public void greetServerStream() {
        LOGGER.info("{} Start server streaming", clientName);
        delegate.greetServerStream("server stream", new StdoutStreamObserver<>("greetServerStream"));
        LOGGER.info("{} Server stream done", clientName);
    }

    public void greetStream() {
        LOGGER.info("{} Start bi streaming", clientName);
        final StreamObserver<String> request = delegate.greetStream(new StdoutStreamObserver<>("greetStream"));
        for (int i = 0; i < 10; i++) {
            request.onNext("stream request");
        }
        request.onCompleted();
        LOGGER.info("{} Bi stream done", clientName);
    }
}
