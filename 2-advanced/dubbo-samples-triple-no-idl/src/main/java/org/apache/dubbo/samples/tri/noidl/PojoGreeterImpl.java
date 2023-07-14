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

package org.apache.dubbo.samples.tri.noidl;

import org.apache.dubbo.common.stream.StreamObserver;;
import org.apache.dubbo.samples.tri.noidl.api.PojoGreeter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PojoGreeterImpl implements PojoGreeter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PojoGreeterImpl.class);

    public PojoGreeterImpl() {}

    @Override
    public String greet(String request) {
        return "hello," + request;
    }

    @Override
    public StreamObserver<String> greetStream(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                LOGGER.info(data);
                response.onNext("hello," + data);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                LOGGER.info("onCompleted");
                response.onCompleted();
            }
        };
    }

    @Override
    public void greetServerStream(String request, StreamObserver<String> response) {
        for (int i = 0; i < 10; i++) {
            response.onNext("hello," + request);
        }
        response.onCompleted();
    }
}
