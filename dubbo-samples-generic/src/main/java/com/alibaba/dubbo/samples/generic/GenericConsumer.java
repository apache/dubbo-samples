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

package com.alibaba.dubbo.samples.generic;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.dubbo.samples.generic.api.Params;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * GenericConsumer
 */
public class GenericConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/generic-consumer.xml"});
        context.start();
        GenericService userService = (GenericService) context.getBean("userService");

        // primary param and return value
        String name = (String) userService.$invoke("delete", new String[]{int.class.getName()}, new Object[]{1});
        System.out.println(name);

        String[] parameterTypes = new String[]{"com.alibaba.dubbo.samples.generic.api.Params"};
        // sample one
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("class", "com.alibaba.dubbo.samples.generic.api.Params");
        param.put("query", "a=b");
        Object user = userService.$invoke("get", parameterTypes, new Object[]{param});
        System.out.println("sample one result: " + user);
        // sample two
        user = userService.$invoke("get", parameterTypes, new Object[]{new Params("a=b")});
        System.out.println("sample two result: " + user);
        System.in.read();
    }
}
