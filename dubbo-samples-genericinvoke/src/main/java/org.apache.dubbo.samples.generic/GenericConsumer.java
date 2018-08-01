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

package org.apache.dubbo.samples.generic;

import com.alibaba.dubbo.rpc.service.GenericService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * GenericConsumer
 */
public class GenericConsumer {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/generic-consumer.xml"});
        context.start();

        GenericService genericService = (GenericService) context.getBean("greetingService");

        Object result = genericService.$invoke("sayHello", new String[]{long[].class.getName(), int[].class.getName(), char[].class.getName()},
                new Object[]{new long[]{1, 2}, new int[]{3, 4}, new char[]{'a', 'b'}});
        System.out.println(result);

        System.in.read();
    }
}
