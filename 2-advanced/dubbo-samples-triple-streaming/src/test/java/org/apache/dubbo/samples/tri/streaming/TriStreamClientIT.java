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
package org.apache.dubbo.samples.tri.streaming;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@EnableDubbo
@ExtendWith(SpringExtension.class)
public class TriStreamClientIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriStreamClientIT.class);

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052", timeout = 3000, proxy = "nativestub")
    private Greeter greeter;

    @Test
    public void biStream() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        StreamObserver<GreeterRequest> requestStreamObserver = greeter.biStream(new SampleStreamObserver(latch));
        for (int i = 0; i < 10; i++) {
            GreeterRequest request = GreeterRequest.newBuilder().setName("name-" + i).build();
            requestStreamObserver.onNext(request);
        }
        requestStreamObserver.onCompleted();
        Assertions.assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void serverStream() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        GreeterRequest request = GreeterRequest.newBuilder().setName("server stream request.").build();
        greeter.serverStream(request, new SampleStreamObserver(latch));
        Assertions.assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    private static class SampleStreamObserver implements StreamObserver<GreeterReply> {

        private final CountDownLatch latch;

        public SampleStreamObserver(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onNext(GreeterReply data) {
            LOGGER.info("stream <- reply:{}", data);
            latch.countDown();
        }

        @Override
        public void onError(Throwable throwable) {
            LOGGER.error("stream onError", throwable);
        }

        @Override
        public void onCompleted() {
            LOGGER.info("stream completed");
        }
    }
}
