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

package org.apache.dubbo.samples.cat.consumer;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.dubbo.samples.cat.api.DemoService;
import org.apache.dubbo.samples.cat.api.context.CatContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CatMetricsIT {

    private static String CAT_HOST = "http://192.168.60.48:8080/cat/r/m/cat-consumer-ac171001-466309-1006?domain=cat-consumer";
    private static final String NAME = "cat-consumer";

    @Resource
    private DemoService demoService;

    @Test
    public void testCat() throws Exception {
        Transaction t = Cat.newTransaction("Call", NAME);
        try {
            Cat.logEvent("Call.server", NAME);
            Cat.logEvent("Call.app", "business");
            Cat.logEvent("Call.port", "20880");
            CatContext catContext = new CatContext();
            Cat.logRemoteCallClient(catContext);
            Assert.assertEquals("hello dubbo and cat", demoService.sayHello("dubbo and cat", catContext));
        } catch (Exception e) {
            Cat.logError(e);
            t.setStatus(e);
        } finally {
            t.setStatus(Transaction.SUCCESS);
            t.complete();
        }
        Thread.sleep(3000);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CAT_HOST).addHeader("accept", "application/json").build();
        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            Assert.assertTrue(json.contains(NAME));
        }
    }

}
