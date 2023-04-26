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

package org.apache.dubbo.sample.tri.api;

import org.apache.dubbo.common.stream.StreamObserver;

import java.util.concurrent.CompletableFuture;

/**
 * Triple supports manual interface with POJO to support migrating from original protocols.
 */
public interface PojoGreeter {

    String SAY_HELLO_01_RESP = "sayHello01";
    String SAY_HELLO_02_RESP = "sayHello02";

    // 1. primitive type
    default String sayHello(int age) {
        return SAY_HELLO_01_RESP;
    }

    // 2. Boxed type
    default String sayHello(Integer age) {
        return SAY_HELLO_02_RESP;
    }

    String methodParamIsNull(String request);


    ParentPojo greetChildPojo(Byte test);

    CompletableFuture<String> unaryFuture(String request);

    String overload();

    String methodNotFound();

    String overload(String param);

    /**
     * <pre>
     *  Sends a greeting
     * </pre>
     */
    String greetLong(int len);

    /**
     * unary
     */
    String greet(String request);

    /**
     * unary
     */
    void greetResponseVoid(String request);

    /**
     * unary
     */
    String sayGreeterRequestVoid();

    String greetException(String request);

    String greetWithAttachment(String request);

    /**
     * bi stream
     */
    StreamObserver<String> greetStream(StreamObserver<String> response);

    StreamObserver<String> greetStreamError(StreamObserver<String> response);

    /**
     * server stream
     */
    void greetServerStream(String request, StreamObserver<String> response);
}
