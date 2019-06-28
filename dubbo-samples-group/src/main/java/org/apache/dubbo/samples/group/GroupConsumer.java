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

package org.apache.dubbo.samples.group;

import org.apache.dubbo.samples.group.api.GroupService;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class GroupConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/group-consumer.xml");
        context.start();

        GroupService groupAService = context.getBean("groupAService", GroupService.class);
        GroupService groupBService = context.getBean("groupBService", GroupService.class);

        String resultGroupA = groupAService.sayHello("world");
        System.out.println(resultGroupA);

        String resultGroupB = groupBService.sayHello("world");
        System.out.println(resultGroupB);
    }
}
