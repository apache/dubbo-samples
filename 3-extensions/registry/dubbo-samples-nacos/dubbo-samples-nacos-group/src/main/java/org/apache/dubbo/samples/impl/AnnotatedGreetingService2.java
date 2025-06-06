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

package org.apache.dubbo.samples.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.api.GreetingService;

import java.util.ArrayList;
import java.util.List;

@DubboService(version = "1.0.0", group = "2")
public class AnnotatedGreetingService2 implements GreetingService {

    @Override
    public String sayHello(String name) {
        System.out.println("greeting service received: " + name);
        return "hello, " + name + " from group 2";
    }

    @Override
    public List<String> sayHelloList(String name) {
        System.out.println("greeting service received: " + name);
        List<String> result = new ArrayList<>();
        result.add("hello, " + name + " from group 2");
        return result;
    }
}
