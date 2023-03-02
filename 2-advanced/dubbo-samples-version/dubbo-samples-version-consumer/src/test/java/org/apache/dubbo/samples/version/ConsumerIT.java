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

package org.apache.dubbo.samples.version;


import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.samples.version.api.VersionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerIT {

    @DubboReference
    private VersionService versionService;

    @Test
    public void test() {
        boolean version1 = false;
        boolean version2 = false;

        for (int i = 0; i < 100; i++) {
            String result = versionService.sayHello("dubbo");
            System.out.println("result: " + result);
            if (result.equals("hello, dubbo")) {
                version1 = true;
            }
            if (result.equals("hello2, dubbo")) {
                version2 = true;
            }
            if (version1&&version2==true)break;
        }
        Assert.assertTrue(version1 && version2);
    }
}
