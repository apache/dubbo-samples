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

package com.alibaba.dubbo.samples.async;

import com.alibaba.dubbo.samples.api.GreetingsServiceAsync;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CompletableFuture;

/**
 * CallbackConsumer
 */
public class AsyncConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/async-consumer.xml"});
        context.start();

        final GreetingsServiceAsync greetingsService = (GreetingsServiceAsync) context.getBean("greetingsService");

        CompletableFuture<String> future = greetingsService.sayHiAsync("async call reqeust");
        System.out.println("async call ret :" + future.get());

        System.out.println(greetingsService.sayHi("normal sync call request"));

        System.in.read();
    }

}
