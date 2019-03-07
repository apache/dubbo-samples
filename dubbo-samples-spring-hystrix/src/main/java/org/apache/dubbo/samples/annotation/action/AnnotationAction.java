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

package org.apache.dubbo.samples.annotation.action;

import org.apache.dubbo.samples.annotation.api.AnnotationService;

import com.alibaba.dubbo.config.annotation.Method;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * AnnotationAction
 */
@Component("annotationAction")
public class AnnotationAction {

    @Reference
    private AnnotationService annotationService;

    @Reference(version = "2.0.0", methods = {@Method(name = "testConsumer", timeout = 200)})
    private AnnotationService annotationService2;

    //    @HystrixCommand(fallbackMethod = "reliable")
    public String doSayHello(String name) {
        return annotationService.sayHello(name);
    }

    public String doSayProvider(String name) {
        return annotationService.testProvider(name);
    }

    public String doSayConsumer(String name) {
        return annotationService.testConsumer(name);
    }

    public String doSayUsual(String name) {
        return annotationService.testUsual(name);
    }

    public String doSayHello2(String name) {
        return annotationService2.sayHello(name);
    }

    public String doSayProvider2(String name) {
        return annotationService2.testProvider(name);
    }

    public String doSayConsumer2(String name) {
        return annotationService2.testConsumer(name);
    }

    public String doSayUsual2(String name) {
        return annotationService2.testUsual(name);
    }
}
