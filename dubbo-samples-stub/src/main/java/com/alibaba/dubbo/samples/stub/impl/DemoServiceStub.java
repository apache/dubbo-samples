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

package com.alibaba.dubbo.samples.stub.impl;

import com.alibaba.dubbo.samples.stub.api.DemoService;

public class DemoServiceStub implements DemoService{

    private final DemoService demoService;

    public DemoServiceStub(DemoService demoService) {
        this.demoService = demoService;
    }

    public String sayHello(String name) {
        //client check code goes here
        System.out.println("stub sayHello");

        try {
            return demoService.sayHello(name);
        } catch (Exception e) {
           //handle Exception
            return null;
        }
    }
}
