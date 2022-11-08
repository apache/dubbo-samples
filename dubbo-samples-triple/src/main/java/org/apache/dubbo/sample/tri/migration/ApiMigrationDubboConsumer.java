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

class ApiMigrationDubboConsumer {
    private final IWrapperGreeter delegate;

    public ApiMigrationDubboConsumer() {
        ReferenceConfig<IWrapperGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(IWrapperGreeter.class);
        ref.setCheck(false);
        ref.setTimeout(3000);
        ref.setProtocol(CommonConstants.DUBBO_PROTOCOL);
        ref.setLazy(true);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("demo-migration-dubbo-consumer"))
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref)
                .start();
        this.delegate = ref.get();
    }

    public static void main(String[] args) {
        final ApiMigrationDubboConsumer consumer = new ApiMigrationDubboConsumer();
        System.out.println("demo-migration-dubbo-consumer dubbo started");
        consumer.sayHelloUnary(CommonConstants.DUBBO_PROTOCOL);
    }

    public void sayHelloUnary(String protocol) {
        System.out.println(delegate.sayHello("unary" + "--" + protocol));
    }

}
