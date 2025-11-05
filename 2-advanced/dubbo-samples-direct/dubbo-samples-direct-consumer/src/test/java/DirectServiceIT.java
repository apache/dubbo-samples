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
import org.apache.dubbo.common.utils.Assert;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.samples.direct.ConsumerApplication;
import org.apache.dubbo.samples.direct.DirectService;
import org.apache.dubbo.samples.direct.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ConsumerApplication.class)
@RunWith(SpringRunner.class)
public class DirectServiceIT {
    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");

    @Autowired
    private Task task;

    @Test
    public void testTask(){
        String result = task.testTask();
        Assert.assertTrue(result.startsWith("Hello world"),result);
    }


    @DubboReference(version = "1.0.0-d",group = "test",check = false,url = "dubbo://${dubbo.address:localhost}:20880")
    private DirectService directService;

    @Test
    public void testDirectService(){
        String result = directService.sayHello("world");
        Assert.assertTrue(result.startsWith("Hello world"),result);
    }

    @Test
    public void testGeneric() throws Exception {

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://" + providerAddress + ":20880/" + DirectService.class.getName());
        reference.setVersion("1.0.0-d");
        reference.setGroup("test");
        reference.setGeneric("true");
        reference.setCheck(false);
        reference.setInterface(DirectService.class.getName());

        ApplicationConfig application = new ApplicationConfig();
        application.setName("direct-consumer");

        DubboBootstrap.getInstance()
                .application(application)
                .reference(reference)
                .start();

        GenericService genericService = DubboBootstrap.getInstance().getCache().get(reference);
        Object obj = genericService.$invoke("sayHello", new String[]{String.class.getName()}, new Object[]{ "generic" });
        String str = (String) obj;
        Assert.assertTrue(str.startsWith("Hello generic"),str);
    }

    @Test
    public void testApi() throws Exception {
        ReferenceConfig<DirectService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://" + providerAddress + ":20880/" + DirectService.class.getName());
        reference.setVersion("1.0.0-d");
        reference.setGroup("test");
        reference.setInterface(DirectService.class.getName());

        ApplicationConfig application = new ApplicationConfig();
        application.setName("direct-consumer");

        DubboBootstrap.getInstance()
                .application(application)
                .reference(reference)
                .start();

        DirectService service = DubboBootstrap.getInstance().getCache().get(DirectService.class);
        String result = service.sayHello("api");
        Assert.assertTrue(result.startsWith("Hello api"),result);
    }
}
