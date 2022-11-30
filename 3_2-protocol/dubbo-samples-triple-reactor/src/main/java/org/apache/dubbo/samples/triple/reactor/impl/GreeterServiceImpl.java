/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.dubbo.samples.triple.reactor.impl;

import org.apache.dubbo.samples.triple.reactor.DubboGreeterServiceTriple;
import org.apache.dubbo.samples.triple.reactor.GreeterReply;
import org.apache.dubbo.samples.triple.reactor.GreeterRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class GreeterServiceImpl extends DubboGreeterServiceTriple.GreeterServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreeterServiceImpl.class);

    @Override
    public Mono<GreeterReply> greetOneToOne(Mono<GreeterRequest> request) {
        return request.doOnNext(req -> LOGGER.info("greetOneToOne get data: {}", req))
                .map(req -> GreeterReply.newBuilder().setMessage(req.getName() + " -> server get").build())
                .doOnNext(res -> LOGGER.info("greetOneToOne response data: {}", res));
    }

    @Override
    public Flux<GreeterReply> greetOneToMany(Mono<GreeterRequest> request) {
        return request.doOnNext(req -> LOGGER.info("greetOneToMany get data: {}", req))
                .flatMapMany(req ->
                        Flux.<String>create(emitter -> {
                                Arrays.stream(req.getName().split(",")).forEach(emitter::next);
                                emitter.complete();
                        })
                )
                .map(n -> GreeterReply.newBuilder().setMessage(n).build())
                .doOnNext(res -> LOGGER.info("greetOneToMany response data: {}", res));
    }

    @Override
    public Mono<GreeterReply> greetManyToOne(Flux<GreeterRequest> request) {
        return request.doOnNext(req -> LOGGER.info("greetManyToOne get data: {}", req))
                .map(req -> Integer.valueOf(req.getName()))
                .reduce(Integer::sum)
                .map(sum -> GreeterReply.newBuilder().setMessage(String.valueOf(sum)).build())
                .doOnNext(res -> LOGGER.info("greetManyToOne response data: {}", res));
    }

    @Override
    public Flux<GreeterReply> greetManyToMany(Flux<GreeterRequest> request) {
        return request.doOnNext(req -> LOGGER.info("greetManyToMany get data: {}", req))
                .map(req -> GreeterReply.newBuilder().setMessage(req.getName() + " -> server get").build())
                .doOnNext(res -> LOGGER.info("greetManyToMany response data: {}", res));
    }
}
