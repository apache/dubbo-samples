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
package org.apache.dubbo.samples.annotation.impl;


import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.samples.annotation.AnnotationConstants;
import org.apache.dubbo.samples.annotation.api.HelloService;

@DubboService(version = AnnotationConstants.VERSION, methods = {@Method(name = "sayGoodbye", timeout = 250, retries = 0)})
public class AnnotationHelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("provider received invoke of sayHello: " + name);
        sleepWhile();
        return "Annotation, hello " + name;
    }

    public String sayGoodbye(String name) {
        System.out.println("provider received invoke of sayGoodbye: " + name);
        sleepWhile();
        return "Goodbye, " + name;
    }

    private void sleepWhile() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
