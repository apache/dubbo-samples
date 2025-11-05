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
package org.apache.dubbo.samples.provider;

import com.alibaba.csp.sentinel.adapter.dubbo3.config.DubboAdapterGlobalConfig;
import com.alibaba.dubbo.rpc.RpcResult;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.samples.sentinel.DemoService;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Collections;

@EnableDubbo
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    // Service level QOS flow control
    @Component
    static class SentinelServiceConfig implements CommandLineRunner {
        @Override
        public void run(String... args) {
            // Limit DemoService to 10 QPS
            FlowRule flowRule = new FlowRule();
            // Note: the resource name here is the interface name.
            flowRule.setResource(DemoService.class.getName());
            flowRule.setCount(10);
            flowRule.setLimitApp("default");
            flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
            FlowRuleManager.loadRules(Collections.singletonList(flowRule));
        }
    }

    // Method level QOS flow control
    @Component
    static class SentinelMethodConfig implements CommandLineRunner {
        @Override
        public void run(String... args) {
            // Limit DemoService.sayHelloAgain() method to 5 QPS.
            FlowRule flowRule = new FlowRule();
            // Note: the resource name here includes the method signature.
            flowRule.setResource(DemoService.class.getName() + ":sayHelloAgain(java.lang.String)");
            flowRule.setCount(5);
            // Note: this will take effect only for the specific consumer whose app name is "sentinel-consumer".
            flowRule.setLimitApp("sentinel-consumer");
            flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
            FlowRuleManager.loadRules(Collections.singletonList(flowRule));
        }
    }

    // Set method gets executed when flow control happens.
    @Component
    static class SentinelCallbackConfig implements CommandLineRunner {
        @Override
        public void run(String... args) {
            DubboAdapterGlobalConfig.setProviderFallback((invoker, invocation, ex) -> {
                System.out.println("Blocked by Sentinel: " + ex.getClass().getSimpleName() + ", " + invocation);
                return AsyncRpcResult.newDefaultAsyncResult(ex.toRuntimeException(), invocation);
            });
        }
    }
}
