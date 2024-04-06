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
package org.apache.dubbo.samples.consumer;

import com.alibaba.csp.sentinel.adapter.dubbo3.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.dubbo.rpc.RpcResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.samples.sentinel.DemoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Component
    static class DemoTask implements CommandLineRunner {
        private static final Logger logger = LoggerFactory.getLogger(DemoTask.class);

        @DubboReference
        private DemoService demoService;

        @Override
        public void run(String... args) {
            // please run triggerProviderFlowControl() or triggerConsumerFlowControl() separately to verify provider and consumer side flow control.
//            triggerProviderFlowControl();
            triggerConsumerFlowControl();
        }

        private void triggerProviderFlowControl() {
            Executors.newScheduledThreadPool(1)
                    .scheduleAtFixedRate(() -> {
                        logger.info("Start to call remote.");
                        for (int i = 0; i < 15; i++) {
                            try {
                                String result = demoService.sayHello("dubbo");
                                demoService.sayHelloAgain("dubbo");
                                logger.info("Call Count:" + i + " Dubbo Remote Return ======> " + result);
                            } catch (RuntimeException ex) {
                                if (ex.getMessage().contains("SentinelBlockException: FlowException")) {
                                    logger.info("Call Count:" + i + " Blocked");
                                } else {
                                    logger.error("Call Count:" + i + " Request Failed.", ex);
                                }
                            }
                        }
                    }, 0, 5000, TimeUnit.MILLISECONDS);
        }

        private void triggerConsumerFlowControl() {
            CountDownLatch latch = new CountDownLatch(5);
            for (int i = 0; i < 8; i++) {
                Executors.newFixedThreadPool(1)
                        .submit(() -> {
                            latch.await();
                            while(true) {
                                logger.info("Start to call remote.");
                                try {
                                    String result = demoService.sayHelloConsumerFlowControl("dubbo");
                                    demoService.sayHelloConsumerDowngrade("dubbo");
                                    logger.info("Call Dubbo Remote Return ======> " + result);
                                }
                                catch (RuntimeException ex) {
                                    if (ex.getMessage().contains("SentinelBlockException: FlowException")) {
                                        logger.info("Call Blocked");
                                    }
                                    else {
                                        logger.error("Call Request Failed.", ex);
                                    }
                                }
                                Thread.sleep(5000);
                            }
                        });
                latch.countDown();
            }
        }
    }

    @Component
    static class SentinelConfig implements CommandLineRunner {
        @Override
        public void run(String... args) {
            FlowRule flowRule = new FlowRule();
            flowRule.setResource("org.apache.dubbo.samples.sentinel.DemoService:sayHelloConsumerFlowControl(java.lang.String)");
            flowRule.setCount(3);
            flowRule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
            FlowRuleManager.loadRules(Collections.singletonList(flowRule));
        }
    }

    @Component
    static class SentinelCallbackConfig implements CommandLineRunner {
        @Override
        public void run(String... args) {
            // Register fallback handler for consumer.
            // If you only want to handle degrading, you need to
            // check the type of BlockException.
            DubboAdapterGlobalConfig.setConsumerFallback((invoker, invocation, ex) -> {
                System.out.println("Blocked by Sentinel: " + ex.getClass().getSimpleName() + ", " + invocation);
                return AsyncRpcResult.newDefaultAsyncResult(ex.toRuntimeException(), invocation);
            });
        }
    }

    @Component
    static class SentinelDowngradeConfig implements CommandLineRunner {
        @Override
        public void run(String... args) {
            List<DegradeRule> rules = new ArrayList<>();
            DegradeRule rule = new DegradeRule();
            rule.setResource("org.apache.dubbo.samples.sentinel.DemoService:sayHelloConsumerDowngrade(java.lang.String)");
            rule.setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType());
            rule.setCount(0.2); // Threshold is 20% error ratio
            rule.setMinRequestAmount(3);
            rule.setStatIntervalMs(10000); // 10s
            rule.setTimeWindow(10);
            rules.add(rule);
            DegradeRuleManager.loadRules(rules);
        }
    }


}
