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

package org.apache.dubbo.samples.annotation;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.samples.annotation.action.AnnotationAction;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

public class AnnotationConsumer {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();

        AnnotationAction annotationAction = context.getBean("annotationAction", AnnotationAction.class);
        String hello = annotationAction.doSayHello("world");
        System.out.println("result :" + hello);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "org.apache.dubbo.samples.annotation.action")
    @PropertySource("classpath:/spring/hystrix-dubbo-consumer.properties")
    @ComponentScan(value = {"org.apache.dubbo.samples.annotation.action"})
    @EnableAspectJAutoProxy
    static public class ConsumerConfiguration {

        @Bean
        public HystrixCommandAspect hystrixCommandAspect() {
            return new HystrixCommandAspect();
        }
    }

}
