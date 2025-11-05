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
import org.apache.dubbo.config.annotation.DubboService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DubboService
public class GreeterImpl extends DubboGreeterTriple.GreeterImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreeterImpl.class);

    @Override
    public StreamObserver<GreeterRequest> biStream(StreamObserver<GreeterReply> responseObserver) {
        return new StreamObserver<GreeterRequest>() {
            @Override
            public void onNext(GreeterRequest data) {
                GreeterReply resp = GreeterReply.newBuilder().setMessage("reply from biStream " + data.getName()).build();
                responseObserver.onNext(resp);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };
    }

    @Override
    public void serverStream(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        LOGGER.info("receive request: {}", request.getName());
        for (int i = 0; i < 10; i++) {
            GreeterReply reply = GreeterReply.newBuilder().setMessage("reply from serverStream. " + i).build();
            responseObserver.onNext(reply);
        }
        responseObserver.onCompleted();
    }
}
