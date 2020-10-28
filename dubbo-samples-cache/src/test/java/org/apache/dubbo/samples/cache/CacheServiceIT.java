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

package org.apache.dubbo.samples.cache;

import org.apache.dubbo.samples.cache.api.CacheService;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/cache-consumer.xml")
public class CacheServiceIT {
    @Autowired
    @Qualifier("cacheService")
    private CacheService service;

    @Test
    public void findCache() throws Exception {
        Assert.assertEquals(service.findCache("0"), service.findCache("0"));
    }

    @Test
    public void verifyLRU() throws Exception {
        String value = service.findCache("0");
        for (int n = 0; n < 1001; n++) {
            TestCase.assertEquals(service.findCache(String.valueOf(n)), service.findCache(String.valueOf(n)));
        }
        Assert.assertEquals(service.findCache("0"), service.findCache("0"));
        Assert.assertNotEquals(value, service.findCache("0"));
    }


}
