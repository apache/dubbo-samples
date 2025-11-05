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
package org.apache.dubbo.samples.chain.impl;

import org.apache.dubbo.samples.chain.api.LionService;

public class LionServiceImpl implements LionService {
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public String getName() {
        System.out.println("I am a Lion!");
        return "I am a Lion!";
    }

    @Override
    public String lion() {
        System.out.println("Lion Lion!");
        return "Lion Lion!";
    }
}
