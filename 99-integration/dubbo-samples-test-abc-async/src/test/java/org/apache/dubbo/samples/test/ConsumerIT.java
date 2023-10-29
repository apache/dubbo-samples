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

import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.samples.test.api.DemoService;

import cn.hutool.core.util.RandomUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/consumer.xml"})
public class ConsumerIT {
    @Autowired
    @Qualifier("demoService1")
    private DemoService demoService;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        String randomStr1 = RandomUtil.randomString(1000);
        Assert.assertEquals(randomStr1, demoService.commonInvoke(randomStr1));

        String randomStr2 = RandomUtil.randomString(1000);
        Assert.assertEquals(randomStr2, demoService.asyncInvoke(randomStr2).get());

        String randomStr3 = RandomUtil.randomString(1000);
        Assert.assertEquals(randomStr3, RpcContext.getClientAttachment().asyncCall(
                () -> demoService.commonInvoke(randomStr3)).get());

        String randomStr4 = RandomUtil.randomString(1000);
        Assert.assertEquals(randomStr4, RpcContext.getClientAttachment().asyncCall(
                () -> demoService.asyncInvoke(randomStr4).get()).get());
    }
}
