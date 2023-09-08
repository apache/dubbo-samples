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

package org.apache.dubbo.tracing.client;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.tracing.api.GreetingsService;
import org.apache.dubbo.tracing.api.TracingConfigProvider;

import java.io.IOException;
import java.util.Date;

public class AlwaysApplication {
    private static final String ZOOKEEPER_HOST = System.getProperty("zookeeper.address", "127.0.0.1");
    private static final String ZOOKEEPER_PORT = System.getProperty("zookeeper.port", "2181");
    private static final String ZOOKEEPER_ADDRESS = "zookeeper://" + ZOOKEEPER_HOST + ":" + ZOOKEEPER_PORT;

    public static void main(String[] args) throws IOException {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setInterface(GreetingsService.class);

        DubboBootstrap.getInstance()
                .application("dubbo-tracing-consumer")
                .registry(new RegistryConfig(ZOOKEEPER_ADDRESS))
                .tracing(TracingConfigProvider.getTracingConfig())
                .reference(reference)
                .start();

        GreetingsService service = reference.get();
        while (true) {
            try {
                String message = service.sayHi("dubbo");
                System.out.println(new Date() + " Receive result ======> " + message);
                Thread.sleep(1000);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

}
