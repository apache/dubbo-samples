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

package org.apache.dubbo.samples.client;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ReferenceBuilder;
import org.apache.dubbo.samples.api.GreetingsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GreetingServiceIT {
    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");

    @Test
    public void test() {
        ReferenceConfig<GreetingsService> reference =
                ReferenceBuilder.<GreetingsService>newBuilder()
                        .interfaceClass(GreetingsService.class)
                        .url("tri://" + providerAddress + ":50052")
                        .build();
        DubboBootstrap.getInstance().reference(reference).start();
        GreetingsService service = reference.get();

        String message = service.sayHi("dubbo");
        Assertions.assertEquals(message, "hi, dubbo");
    }
}
