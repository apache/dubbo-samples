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

package org.apache.dubbo.samples.resilience4jboot2.consumer;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.monitoring.health.CircuitBreakerHealthIndicator;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * CallbackConsumer
 */
@SpringBootApplication
//@EnableSpringBootMetricsCollector
//@EnablePrometheusEndpoint
@EnableConfigurationProperties
@EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.resilience4jboot2.consumer.action")
@PropertySource("classpath:/spring/dubbo-consumer.properties")
public class Resilience4jBootConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jBootConsumerApplication.class, args);
    }

    @Bean
    public HealthIndicator backendA(CircuitBreakerRegistry circuitBreakerRegistry){
        return new CircuitBreakerHealthIndicator(circuitBreakerRegistry.circuitBreaker("backendA"));
    }

    @Bean
    public HealthIndicator backendB(CircuitBreakerRegistry circuitBreakerRegistry){
        return new CircuitBreakerHealthIndicator(circuitBreakerRegistry.circuitBreaker("backendB"));
    }

}
