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

package org.apache.dubbo.samples.basic.api;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DemoService {

    String sayHello(String name);

    Mono<String> requestMono(String name);

    Mono<String> requestMonoBizError(String name);

    Flux<String> requestFlux(String name);

    Mono<String> requestMonoWithMonoArg(Mono<String> m1, Mono<String> m2);

    Flux<String> requestFluxWithFluxArg(Flux<String> f1, Flux<String> f2);

}
