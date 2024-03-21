/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.apache.dubbo.samples.rpccontext;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.rpccontext.api.RpcContextService1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class RpcContextTester implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcContextTester.class);
    @DubboReference(interfaceClass = RpcContextService1.class)
    private RpcContextService1 rpcContextService1;

    @Override
    public void run(ApplicationArguments args) {
        rpcContextService1.sayHello();
        LOGGER.info("rpc context tester done");
    }

}
