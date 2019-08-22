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

package org.apache.dubbo.samples.annotation;

import org.apache.dubbo.samples.annotation.action.AnnotationAction;

import org.apache.dubbo.samples.annotation.config.ConsumerConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConsumerConfiguration.class})
public class AnnotationServicesIT {
    @Autowired
    private AnnotationAction annotationAction;

    @Test
    public void testSayHello() throws Exception {
        Assert.assertEquals("Annotation, hello dubbo", annotationAction.doSayHello("dubbo"));
    }

    @Test
    public void testSayGoodbye() throws Exception {
        Assert.assertEquals("Throw Exception", annotationAction.doSayGoodbye("dubbo"));
    }

    @Test
    public void testGreeting() throws Exception {
        Assert.assertEquals("Annotation, greeting dubbo", annotationAction.doGreeting("dubbo"));
    }

    @Test
    public void testReplyGreeting() throws Exception {
        Assert.assertEquals("Throw Exception", annotationAction.replyGreeting("dubbo"));
    }
}
