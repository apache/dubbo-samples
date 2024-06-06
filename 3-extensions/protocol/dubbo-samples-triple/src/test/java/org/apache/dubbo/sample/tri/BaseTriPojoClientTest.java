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
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.sample.tri.api.ParentPojo;
import org.apache.dubbo.sample.tri.api.PojoGreeter;
import org.apache.dubbo.sample.tri.util.StdoutStreamObserver;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class BaseTriPojoClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTriPojoClientTest.class);


    protected static PojoGreeter delegate;

    protected static PojoGreeter longDelegate;

    protected static DubboBootstrap appDubboBootstrap;


    @Test
    public void unaryFuture() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Map<String, Integer> map = new HashMap<>();
        map.put("val", 1);
        CompletableFuture<String> future = delegate.unaryFuture("unaryFuture");
        future.whenComplete((s, throwable) -> {
            if ("unaryFuture".equals(s)) {
                map.put("val", map.get("val") + 1);
            }
            latch.countDown();
        });
        map.put("val", 2);
        latch.await(3, TimeUnit.SECONDS);
        Assert.assertEquals(3, map.get("val").intValue());
    }

    @Test
    public void overload() {
        String ret = delegate.overload();
        Assert.assertEquals(ret, "overload");
        String ret2 = delegate.overload("overload");
        Assert.assertEquals(ret2, "overload");
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
    public void testBoxed() {
        // 01 primitive
        String resp01 = delegate.sayHello(1);
        Assert.assertEquals(PojoGreeter.SAY_HELLO_01_RESP, resp01);

        // 02 boxed
        String resp02 = delegate.sayHello((Integer) 2);
        Assert.assertEquals(PojoGreeter.SAY_HELLO_02_RESP, resp02);
    }
    @Test
    public void greetChildPojo() {
        Byte byte1 = Byte.valueOf("1");

        ParentPojo childPojo = delegate.greetChildPojo(byte1);

        Assert.assertEquals(byte1, childPojo.getByte1());
    }

    @Test
    public void greetMethodParamIsNull() {
        String ret = delegate.methodParamIsNull(null);
        Assert.assertEquals(ret, "ok");
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
