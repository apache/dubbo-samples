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

package org.apache.dubbo.samples.action;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.Greeter;
import org.apache.dubbo.samples.GreeterReply;
import org.apache.dubbo.samples.GreeterRequest;
import org.apache.dubbo.samples.util.StdoutStreamObserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("annotatedConsumer")
public class GreetingServiceConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingServiceConsumer.class);

    @DubboReference(version = "1.0.0", providedBy = "dubbo-samples-mesh-provider", lazy = true, providerPort = 50052)
    private Greeter greeter;

    public void doSayHello(String name) {
        final GreeterReply reply = greeter.greet(GreeterRequest.newBuilder()
            .setName(name)
            .build());
        LOGGER.info("consumer Unary reply <-{}", reply);
    }

    public void doBISayHello(String name) {
        String clientName = "consumer";
        final StreamObserver<GreeterRequest> request = greeter.greetStream(new StdoutStreamObserver<>(name));
        for (int i = 0; i < 10; i++) {
            LOGGER.info("{} Send stream request-> {}", clientName, i);
            request.onNext(GreeterRequest.newBuilder()
                .setName("request")
                .build());
        }
        request.onCompleted();
    }

    public void getHelloFromServer() {
        GreeterRequest greeterRequest = GreeterRequest.newBuilder().setName("consumer").build();
        greeter.greetServerStream(greeterRequest, new StdoutStreamObserver<>("consumer"));
    }

}
