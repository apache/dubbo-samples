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

package org.apache.dubbo.samples.metrics.prometheus.api;

import org.apache.dubbo.samples.metrics.prometheus.api.model.Result;
import org.apache.dubbo.samples.metrics.prometheus.api.model.User;

import java.util.concurrent.CompletableFuture;

public interface DemoService {

    CompletableFuture<Integer> sayHello();

    Result sayHello(String name);

    Result sayHello(Long id, String name);

    Result sayHello(User user);

    String stringArray(String[] bytes);

    Result timeLimitedMethod(String name) throws InterruptedException;

    Result randomResponseTime(String name) throws InterruptedException;

    Result runTimeException(String name);

}
