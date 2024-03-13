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

package org.apache.dubbo.samples.tri.grpc.interop.server;

import java.io.IOException;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class TriOpServer {
    public static void main(String[] args) throws IOException {
          SpringApplication.run(TriOpServer.class);
/*
     THIS IS THE REPLACED API CONFIGURATION METHOD
        ServiceConfig<Greeter> service = new ServiceConfig<>();
        service.setInterface(Greeter.class);
        service.setRef(new TriGreeterImpl());

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("tri-stub-server"))
                .registry(new RegistryConfig("N/A"))
                .protocol(new ProtocolConfig(CommonConstants.TRIPLE, 9999))
                .service(service)
                .start()
                .await();
*/
    }
}
