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

package com.apache.dubbo.sample.basic;

import java.util.concurrent.CountDownLatch;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

public class StreamProvider {
    public static void main(String[] args) throws InterruptedException {
        ServiceConfig<IStreamGreeter> service = new ServiceConfig<>();
        service.setInterface(IStreamGreeter.class);
        service.setRef(new IStreamGreeterImpl());
        service.setProtocol(new ProtocolConfig(CommonConstants.TRIPLE, 50051));
        service.setApplication(new ApplicationConfig("stream-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        service.export();
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
