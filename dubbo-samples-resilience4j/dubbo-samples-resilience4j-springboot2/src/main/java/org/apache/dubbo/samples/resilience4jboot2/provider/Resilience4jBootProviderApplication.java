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

package org.apache.dubbo.samples.resilience4jboot2.provider;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

/**
 * MergeProvider
 */
@SpringBootApplication
//@EnableSpringBootMetricsCollector
//@EnablePrometheusEndpoint
@EnableConfigurationProperties
public class Resilience4jBootProviderApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException {
        SpringApplication application = new SpringApplication(new Class<?>[] { Resilience4jBootProviderApplication.class });
        application.setAdditionalProfiles("provider");
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.currentThread().join();
    }

//    @Bean
//    public HealthIndicator backendA(CircuitBreakerRegistry circuitBreakerRegistry){
//        return new CircuitBreakerHealthIndicator(circuitBreakerRegistry.circuitBreaker("backendA"));
//    }
//
//    @Bean
//    public HealthIndicator backendB(CircuitBreakerRegistry circuitBreakerRegistry){
//        return new CircuitBreakerHealthIndicator(circuitBreakerRegistry.circuitBreaker("backendB"));
//    }

//    @Bean
//    public ProviderConfig providerConfig() {
//        ProviderConfig providerConfig = new ProviderConfig();
//        providerConfig.setTimeout(1000);
//        return providerConfig;
//    }

}
