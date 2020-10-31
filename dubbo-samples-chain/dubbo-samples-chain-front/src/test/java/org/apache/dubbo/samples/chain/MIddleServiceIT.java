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

package org.apache.dubbo.samples.chain;

import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.samples.chain.api.AmericanService;
import org.apache.dubbo.samples.chain.api.CatService;
import org.apache.dubbo.samples.chain.api.ChineseService;
import org.apache.dubbo.samples.chain.api.DogService;
import org.apache.dubbo.samples.chain.api.LionService;
import org.apache.dubbo.samples.chain.api.TigerService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/dubbo-demo-consumer.xml")
public class MIddleServiceIT {
    @Autowired
    @Qualifier("americanService")
    private AmericanService americanService;

    @Autowired
    @Qualifier("chineseService")
    private ChineseService chineseService;

    @Autowired
    private CatService catService;

    @Autowired
    private DogService dogService;

    @Autowired
    private LionService lionService;

    @Autowired
    private TigerService tigerService;

    @Test
    public void americanWatch() throws Exception {
        Assert.assertEquals(americanService.watch(), "I want to see Lion!");
    }

    @Test(expected = RpcException.class)
    public void americanEat() throws Exception {
        americanService.eat();
    }

    @Test
    public void chineseWatch() throws Exception {
        Assert.assertEquals(chineseService.watch(), "I want to see Tiger!");
    }

    @Test(expected = RpcException.class)
    public void chineseEat() throws Exception {
        chineseService.eat();
    }

    @Test
    public void cat() throws Exception {
        Assert.assertEquals(1, catService.getId());
        Assert.assertEquals("I am a Cat!", catService.getName());
        Assert.assertEquals("Meow Meow!", catService.cat());
    }

    @Test
    public void dog() throws Exception {
        Assert.assertEquals(0, dogService.getId());
        try {
            dogService.getName();
            fail("timeout should happen");
        } catch (Throwable t) {

        }
        Assert.assertEquals("Woof Woof!", dogService.dog());
    }

    @Test
    public void lion() throws Exception {
        Assert.assertEquals(2, lionService.getId());
        Assert.assertEquals("I am a Lion!", lionService.getName());
        Assert.assertEquals("Lion Lion!", lionService.lion());
    }

    @Test
    public void tiger() throws Exception {
        Assert.assertEquals(3, tigerService.getId());
        Assert.assertEquals("I am a Tiger!", tigerService.getName());
        Assert.assertEquals("Tiger Tiger!", tigerService.tiger());
    }
}
