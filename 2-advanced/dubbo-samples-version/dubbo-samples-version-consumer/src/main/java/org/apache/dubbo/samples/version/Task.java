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
package org.apache.dubbo.samples.version;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.version.api.VersionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Task implements CommandLineRunner {
    @DubboReference(version = "*",loadbalance="roundrobin",client = "myNetty")
    private VersionService versionService;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            String hello = versionService.sayHello("world");
            System.out.println(hello);
            Thread.sleep(200);
        }
    }
}
