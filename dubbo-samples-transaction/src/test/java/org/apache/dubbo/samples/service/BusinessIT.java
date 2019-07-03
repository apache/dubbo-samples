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

package org.apache.dubbo.samples.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/dubbo-business.xml"})
public class BusinessIT {
    @Autowired
    @Qualifier("business")
    private BusinessService business;

    @Autowired
    private StorageService storageService;

    @Test
    public void testRollback() throws Exception {
        int before = storageService.queryCount("C00321");
        try {
            business.purchase("U100001", "C00321", 2);
            fail("BusinessService.purchase should throw exception.");
        } catch (Throwable t) {
            // ignore
        }
        int after = storageService.queryCount("C00321");
        Assert.assertEquals(before, after);
    }

    @Test
    public void testCommit() throws Exception {
        // TODO: need implement a success commit
    }
}
