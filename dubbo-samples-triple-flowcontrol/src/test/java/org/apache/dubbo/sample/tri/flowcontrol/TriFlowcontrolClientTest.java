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
package org.apache.dubbo.sample.tri.flowcontrol;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.sample.tri.flowcontrol.api.PojoGreeter;
import org.apache.dubbo.sample.tri.flowcontrol.util.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.flowcontrol.util.TriSampleConstants;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TriFlowcontrolClientTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(TriFlowcontrolClientTest.class);

    private static PojoGreeter delegate;

    protected static PojoGreeter longDelegate;

    protected static DubboBootstrap appDubboBootstrap;

    @BeforeClass
    public static void initStub() {
        ReferenceConfig<PojoGreeter> ref = new ReferenceConfig<>();
        ref.setInterface(PojoGreeter.class);
        ref.setTimeout(3000);
        ref.setRetries(0);
        ref.setProtocol(CommonConstants.TRIPLE);

        ReferenceConfig<PojoGreeter> ref2 = new ReferenceConfig<>();
        ref2.setInterface(PojoGreeter.class);
        ref2.setTimeout(15000);
        ref2.setRetries(0);
        ref2.setProtocol(CommonConstants.TRIPLE);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        ApplicationConfig applicationConfig = new ApplicationConfig(TriFlowcontrolClientTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
        System.setProperty("dubbo.rpc.tri.initial-window-size","65535");
        bootstrap.application(applicationConfig)
                .registry(new RegistryConfig(TriSampleConstants.ZK_ADDRESS))
                .reference(ref)
                .reference(ref2)
                .start();
        delegate = ref.get();
        longDelegate = ref2.get();
        appDubboBootstrap = bootstrap;
    }


    @Test
    public void greetStreamFlowControl() throws InterruptedException {
        int n = 2000;
        CountDownLatch latch = new CountDownLatch(n);
        final StreamObserver<String> request = delegate.greetStream(new StdoutStreamObserver<String>(
                "greetStream") {
            @Override
            public void onNext(String data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        Throwable throwable = new Exception();
        int times = 10000;
        while(true){
            try{
                if(times <= 0){
                    break;
                }
                request.onNext(getRandomStr());
                Thread.sleep(1);
                times--;
            }catch (Exception e){
                throwable = e;
                break;
            }
        }
        Assert.assertEquals("Due flowcontrol over pendingbytes, Call already canceled",throwable.getMessage());

    }


    public String getRandomStr(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random1=new Random();
        //指定字符串长度，拼接字符并toString
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < 1000; i++) {
            //获取指定长度的字符串中任意一个字符的索引值int number=random1.nextInt(str.length());
            //根据索引值获取对应的字符char charAt = str.charAt(number);
            int num = random1.nextInt(62);
            sb.append(str.charAt(num));
        }
        return sb.toString();
    }
}
