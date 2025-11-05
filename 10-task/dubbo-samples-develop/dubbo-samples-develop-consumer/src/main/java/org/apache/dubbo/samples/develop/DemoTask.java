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
package org.apache.dubbo.samples.develop;


import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoTask implements CommandLineRunner {
    @DubboReference(group = "group1",version = "1.0")
    private DevelopService developService;

    @DubboReference(group = "group2",version = "2.0")
    private DevelopService developServiceV2;

    @Override
    public void run(String... args) throws Exception {
        //调用DevelopService的group1分组实现
        System.out.println("Dubbo Remote Return ======> " + developService.invoke("1"));
        //调用DevelopService的另一个实现
        System.out.println("Dubbo Remote Return ======> " + developServiceV2.invoke("2"));
    }
}