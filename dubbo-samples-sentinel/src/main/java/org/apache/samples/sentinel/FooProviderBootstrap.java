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

package org.apache.samples.sentinel;

import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.apache.samples.sentinel.provider.ProviderConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;

/**
 * Provider demo for Apache Dubbo 2.7.x or above. Please add the following VM arguments:
 * <pre>
 * -Dcsp.sentinel.api.port=8720
 * -Dproject.name=dubbo-provider-demo
 * </pre>
 */
public class FooProviderBootstrap {

    private static final String INTERFACE_RES_KEY = FooService.class.getName();
    private static final String RES_KEY = INTERFACE_RES_KEY + ":sayHello(java.lang.String)";

    public static void main(String[] args) throws InterruptedException {
        // Users don't need to manually call this method.
        // Only for eager initialization.
        InitExecutor.doInit();

        initFlowRule();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ProviderConfiguration.class);
        context.refresh();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }

    private static void initFlowRule() {
        FlowRule flowRule = new FlowRule(INTERFACE_RES_KEY)
                .setCount(10)
                .setGrade(RuleConstant.FLOW_GRADE_QPS);
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));
    }
}
