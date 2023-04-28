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
package org.apache.dubbo.samples.impl;

import org.apache.dubbo.samples.api.ManagementService;
import org.apache.dubbo.samples.provider.DubboThreadPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class ManagementServiceImpl implements ManagementService {
    private final Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    public ManagementServiceImpl() {
        counters.put("dubbo-h", new AtomicInteger(0));
        counters.put("dubbo-m", new AtomicInteger(0));
        counters.put("dubbo-l", new AtomicInteger(0));
        counters.put("dubbo-danger", new AtomicInteger(0));
        counters.put("dubbo-openapi", new AtomicInteger(0));
    }

    @Override
    public Map<String, AtomicInteger> getCounters() {
        return counters;
    }

    @Override
    public Map<Integer, Integer> getExecutorsSize() {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        for (Map.Entry<Integer, Executor> entry : DubboThreadPool.executors.entrySet()) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) entry.getValue();
            map.put(entry.getKey(), executor.getPoolSize());
        }
        return map;
    }
}
