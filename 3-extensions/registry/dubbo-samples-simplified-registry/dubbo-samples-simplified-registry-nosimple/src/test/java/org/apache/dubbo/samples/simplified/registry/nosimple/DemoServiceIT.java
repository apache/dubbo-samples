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

package org.apache.dubbo.samples.simplified.registry.nosimple;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.samples.simplified.registry.nosimple.api.DemoService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/simplified-consumer.xml"})
public class DemoServiceIT {
    @Autowired
    private DemoService demoService;

    @Test
    public void verifyProvider() throws Exception {
        List<String> urls = ZkUtil.getChildren(ZkUtil.toUrlPath("providers"));
        urls.stream().map(URL::decode).forEach(System.out::println);
        Assert.assertEquals(1, urls.size());
        String url = urls.get(0);
        Assert.assertTrue(url.contains("retries"));
        Assert.assertTrue(url.contains("owner"));
        Assert.assertTrue(url.contains("timeout"));
        Assert.assertTrue(url.contains("version"));
        Assert.assertTrue(url.contains("group"));
        Assert.assertTrue(url.contains("release"));
        Assert.assertTrue(url.contains("executes"));
    }

    @Test
    public void verifyConsumer() throws Exception {
        List<String> urls = ZkUtil.getChildren(ZkUtil.toUrlPath("consumers"));
        urls.stream().map(URL::decode).forEach(System.out::println);
        Assert.assertEquals(1, urls.size());
        String url = urls.get(0);

        Assert.assertTrue(url.contains("retries"));
        Assert.assertTrue(url.contains("timeout"));
        Assert.assertTrue(url.contains("owner"));
        Assert.assertTrue(url.contains("actives"));
        Assert.assertTrue(url.contains("application"));
        Assert.assertTrue(url.contains("version"));
        Assert.assertTrue(url.contains("group"));
        Assert.assertTrue(url.contains("release"));
    }
}
