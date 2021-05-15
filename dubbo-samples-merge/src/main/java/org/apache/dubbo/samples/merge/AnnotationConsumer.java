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

package org.apache.dubbo.samples.merge;

//import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.cluster.Constants;
import org.apache.dubbo.samples.merge.api.MergeService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.dubbo.common.Constants.TAG_KEY;

/**
 * CallbackConsumer
 */
public class AnnotationConsumer {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        MergeService mergeService = (MergeService) context.getBean("mergeService");
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                RpcContext.getContext().setAttachment(TAG_KEY, "vvvttt");
                List<String> result = mergeService.mergeResult();
                System.out.println("(" + i + ") " + result);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Configuration
    @ImportResource("spring/merge-consumer2.xml")
    static public class ConsumerConfiguration {
        @Bean
        public ApplicationConfig applicationConfig() {
            ApplicationConfig applicationConfig = new ApplicationConfig();
            applicationConfig.setName("consumer-book6");
            applicationConfig.setQosEnable(false);
            Map<String,String> parameters = new HashMap<>();
            parameters.put(Constants.ROUTER_KEY, "tag");
            applicationConfig.setParameters(parameters);
            return applicationConfig;
        }
    }

}
