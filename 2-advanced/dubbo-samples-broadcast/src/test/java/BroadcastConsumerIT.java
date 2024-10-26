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

import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.registry.client.migration.MigrationInvoker;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.samples.broadcast.api.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Consumer test side
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/broadcast-consumer.xml")
public class BroadcastConsumerIT {

    @Autowired
    @Qualifier("demoService")
    private DemoService demoService;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSayHello() {
        Assert.assertTrue(demoService.sayHello("world").contains("Hello"));
    }

    @Test
    public void testBroadcast() {
        Assert.assertTrue(demoService.sayHello("world").contains("Hello"));
        assertInvoke();
    }

    /**
     * Call all invokers manually
     */
    public void assertInvoke() {
        ReferenceBean reference = applicationContext.getBean(ReferenceBean.class);

        Invocation invocation = new RpcInvocation("isInvoke", "org.apache.dubbo.samples.broadcast.api.DemoService", "", new Class<?>[0], new Object[0]);

        List<Invoker> allInvokers = (List<Invoker>) ((MigrationInvoker) reference.getReferenceConfig().getInvoker()).getInvoker().getDirectory().getAllInvokers();
        for (Invoker allInvoker : allInvokers) {
            Result invoke = allInvoker.invoke(invocation);
            Assert.assertTrue((boolean) invoke.getValue());
        }
    }

}
