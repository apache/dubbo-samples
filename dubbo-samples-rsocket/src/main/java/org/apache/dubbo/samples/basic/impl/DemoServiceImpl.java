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

package org.apache.dubbo.samples.basic.impl;

import org.apache.dubbo.samples.basic.api.DemoService;

import com.alibaba.dubbo.rpc.RpcContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DemoServiceImpl implements DemoService {

    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext
            .getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }


    public Mono<String> requestMono(String name) {
        return Mono.just("hello " + name);
    }

    public Mono<String> requestMonoBizError(String name) {
        int a = 1 / 0;
        return Mono.just("hello " + name);
    }

    @Override
    public Flux<String> requestFlux(String name) {

        return Flux.create(new Consumer<FluxSink<String>>() {
            @Override
            public void accept(FluxSink<String> fluxSink) {
                for (int i = 0; i < 5; i++) {
                    fluxSink.next(name + " " + i);
                }
                fluxSink.complete();
            }
        });

    }

    @Override
    public Mono<String> requestMonoWithMonoArg(Mono<String> m1, Mono<String> m2) {
        return m1.zipWith(m2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                return s+" "+s2;
            }
        });
    }

    @Override
    public Flux<String> requestFluxWithFluxArg(Flux<String> f1, Flux<String> f2) {
        return f1.zipWith(f2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                return s+" "+s2;
            }
        });
    }

}
