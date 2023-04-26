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

package org.apache.dubbo.samples.merge;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.merge.api.MergeService;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DubboAutoConfiguration.class})
public class Consumer2IT {
    @DubboReference(group = "merge2,merge3")
    private MergeService mergeService;

    @Test
    public void test() {
        List<String> result = mergeService.mergeResult();
        Assert.assertTrue(result.contains("group-2.1"));
        Assert.assertTrue(result.contains("group-2.2"));
        Assert.assertEquals(2, result.size());
    }
}
