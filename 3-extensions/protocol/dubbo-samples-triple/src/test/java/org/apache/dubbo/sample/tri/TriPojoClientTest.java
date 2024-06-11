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

package org.apache.dubbo.sample.tri;

import org.apache.dubbo.common.Version;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.generic.GenericClient;
import org.apache.dubbo.sample.tri.util.StdoutStreamObserver;
import org.apache.dubbo.sample.tri.util.TriSampleConstants;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TriPojoClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericClient.class);

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
        ApplicationConfig applicationConfig = new ApplicationConfig(TriPojoClientTest.class.getName());
        applicationConfig.setMetadataServicePort(TriSampleConstants.CONSUMER_METADATA_SERVICE_PORT);
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
    public void greetUnaryRequestVoid() {
        Assert.assertEquals("hello!void", delegate.sayGreeterRequestVoid());
    }

    @Test
    public void greetUnaryResponseVoid() {
        delegate.greetResponseVoid("void");
    }

    @Test
    public void greetUnary() {
        Assert.assertEquals("hello,unary", delegate.greet("unary"));
    }

    @Test
    public void greetException() {
        boolean isSupportSelfDefineException = Version.getVersion().compareTo("3.2.0") < 0;
        try {
            delegate.greetException("exception");
            Assert.fail();
        } catch (RpcException e) {
            Assert.assertEquals(isSupportSelfDefineException, true);
        } catch (IllegalStateException e) {
            Assert.assertEquals(isSupportSelfDefineException, false);
        }
    }

    @Test
    public void greetServerStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        delegate.greetServerStream("server stream", new StdoutStreamObserver<String>("greetServerStream") {
            @Override
            public void onNext(String data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));


        delegate.greetServerStream("server stream", new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                LOGGER.info(data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }
        });


        StreamObserver<String> request = delegate.greetStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                LOGGER.info(data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
            }
        });
        for (int i = 0; i < n; i++) {
            request.onNext("stream request" + i);
        }
        request.onCompleted();


    }

    @Test
    public void greetStream() throws InterruptedException {
        int n = 10;
        CountDownLatch latch = new CountDownLatch(n);
        final StreamObserver<String> request = delegate.greetStream(new StdoutStreamObserver<String>(
                "greetStream") {
            @Override
            public void onNext(String data) {
                super.onNext(data);
                latch.countDown();
            }
        });
        for (int i = 0; i < n; i++) {
            request.onNext("stream request");
        }
        request.onCompleted();
        Assert.assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void greetLong() {
        int power = 23;
        for (int i = 0; i < power; i++) {
            final int len = (1 << i);
            final String response = longDelegate.greetLong(len);
            LOGGER.info("Response len:" + response.length());
            Assert.assertEquals(len, response.length());
        }
    }

    @AfterClass
    public static void alterTest() {
        appDubboBootstrap.stop();
        DubboBootstrap.reset();
    }
}
