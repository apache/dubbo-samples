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

package org.apache.dubbo.samples.tri.unary;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ApplicationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class TriUnaryClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriUnaryClient.class);

    public static void main(String[] args) throws IOException {
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(ApplicationBuilder.newBuilder().name("dubbo-samples-api-idl-client").logger("slf4j").build());

        ReferenceConfig<Greeter> ref = new ReferenceConfig<>();
        ref.setInterface(Greeter.class);
        ref.setUrl("tri://localhost:50052");

        bootstrap.reference(ref).start();
        Greeter greeter = ref.get();

        //sync
        unarySync(greeter);

        //async
        unaryAsync(greeter);
    }

    private static void unarySync(Greeter greeter) {
        LOGGER.info("{} Start unary", "tri-unary-client");
        final GreeterReply reply = greeter.greet(GreeterRequest.newBuilder().setName("name").build());
        LOGGER.info("{} Unary reply <-{}", "tri-unary-client", reply);
    }

    private static void unaryAsync(Greeter greeter) {
        CompletableFuture<GreeterReply> greetAsync = greeter.greetAsync(GreeterRequest.newBuilder().setName("name").build());
        greetAsync.whenComplete((result, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.println("greet async: " + result.getMessage());
            }
        });
    }
}
