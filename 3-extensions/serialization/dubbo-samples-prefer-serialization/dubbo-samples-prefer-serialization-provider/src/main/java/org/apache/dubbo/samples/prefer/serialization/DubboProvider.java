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

package org.apache.dubbo.samples.prefer.serialization;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboProvider {

    public static void main(String[] args) throws Exception {
        System.out.println("Java serialization is unsafe. Dubbo Team do not recommend anyone to use it." +
                "If you still want to use it, please follow [JEP 290](https://openjdk.java.net/jeps/290)" +
                "to set serialization filter to prevent deserialization leak.");

        new EmbeddedZooKeeper(2181, false).start();

        SpringApplication.run(DubboProvider.class);

        System.out.println("dubbo service started");
    }
}
