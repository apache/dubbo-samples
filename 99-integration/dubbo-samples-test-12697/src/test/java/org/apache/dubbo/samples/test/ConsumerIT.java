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
package org.apache.dubbo.samples.test;

import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.samples.test.api.DemoService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/consumer.xml"})
public class ConsumerIT {
    @Autowired
    private ApplicationModel applicationModel;

    @Autowired
    @Qualifier("demoService1")
    private DemoService demoService1;

    @Autowired
    @Qualifier("demoService2")
    private DemoService demoService2;

    @Autowired
    @Qualifier("demoService3")
    private DemoService demoService3;

    @Test
    public void test() {
        Assert.assertEquals(3, applicationModel.getFrameworkModel().getServiceRepository().allConsumerModels().size());
    }
}
