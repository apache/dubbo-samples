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
package org.apache.dubbo.samples.d;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.ServiceD;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

@DubboService
public class ServiceDImpl implements ServiceD {

    private String localHost;

    public ServiceDImpl() {
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            localHost = "unknown host";
        }
    }

    @Override
    public String sayHello(String name) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100));
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
        return "Hello, " + name + ". " +
                "I am App D from " + localHost + ".\n";
    }
}
