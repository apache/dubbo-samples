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

package org.apache.dubbo.sample.tri.util;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.sample.tri.Greeter;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.apache.dubbo.sample.tri.stub.TriStubClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriSampleClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriStubClient.class);
    private Greeter greeter;
    private String clientName;

    public void unary() {
        LOGGER.info("{} Start unary", clientName);
        final GreeterReply reply = greeter.greet(GreeterRequest.newBuilder()
                .setName("name")
                .build());
        LOGGER.info("{} Unary reply <-{}", clientName, reply);
    }

    public void serverStream() {
        LOGGER.info("{} Start server streaming", clientName);
        greeter.greetServerStream(GreeterRequest.newBuilder()
                .setName("request")
                .build(), new StdoutStreamObserver<>("serverStream"));
        LOGGER.info("{} Server stream done", clientName);
    }

    public void stream() {
        LOGGER.info("{} Start bi streaming", clientName);
        final StreamObserver<GreeterRequest> request = greeter.greetStream(new StdoutStreamObserver<>("stream"));
        for (int i = 0; i < 10; i++) {
            LOGGER.info("{} Send stream request-> {}", clientName, i);
            request.onNext(GreeterRequest.newBuilder()
                    .setName("request")
                    .build());
        }
        LOGGER.info("{} Bi stream done", clientName);
        request.onCompleted();
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setGreeter(Greeter greeter) {
        this.greeter = greeter;
    }
}
