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

package org.apache.dubbo.sample.tri.migration;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

class ApiMigrationTriConsumer {
    private final IWrapperGreeter triDelegate;

    public ApiMigrationTriConsumer() {
        ReferenceConfig<IWrapperGreeter> ref2 = new ReferenceConfig<>();
        ref2.setInterface(IWrapperGreeter.class);
        ref2.setCheck(false);
        ref2.setTimeout(3000);
        ref2.setProtocol(CommonConstants.TRIPLE);
        ref2.setLazy(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-migration-tri-consumer"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref2)
                .start();
        this.triDelegate = ref2.get();
    }

    public static void main(String[] args) {
        final ApiMigrationTriConsumer consumer = new ApiMigrationTriConsumer();
        System.out.println("demo-migration-both-consumer started");
        consumer.sayTriHelloUnary(CommonConstants.TRIPLE);
    }

    public void sayTriHelloUnary(String protocol) {
        System.out.println(triDelegate.sayHello("unary" + "--" + protocol));
    }

}
