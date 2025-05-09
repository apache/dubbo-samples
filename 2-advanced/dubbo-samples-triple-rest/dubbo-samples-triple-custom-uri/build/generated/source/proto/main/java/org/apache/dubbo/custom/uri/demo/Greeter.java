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

package org.apache.dubbo.custom.uri.demo;

import org.apache.dubbo.common.stream.StreamObserver;
import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public interface Greeter extends org.apache.dubbo.rpc.model.DubboStub {

    String JAVA_SERVICE_NAME = "org.apache.dubbo.custom.uri.demo.Greeter";
    String SERVICE_NAME = "helloworld.Greeter";
    /**
         * <pre>
         *  User&#39;s name
         * </pre>
         */
    org.apache.dubbo.custom.uri.demo.HelloReply sayHelloWithPost(org.apache.dubbo.custom.uri.demo.HelloRequest request);

    CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> sayHelloWithPostAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request);



    /**
         * <pre>
         *  Update the greeting using PUT method
         * </pre>
         */
    org.apache.dubbo.custom.uri.demo.HelloReply updateGreeting(org.apache.dubbo.custom.uri.demo.HelloRequest request);

    CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> updateGreetingAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request);



    /**
         * <pre>
         *  Health check interface, maps request body to HelloRequest
         * </pre>
         */
    org.apache.dubbo.custom.uri.demo.HelloReply healthCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request);

    CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> healthCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request);



    /**
         * <pre>
         *  Health check interface
         * </pre>
         */
    org.apache.dubbo.custom.uri.demo.HelloReply checkName(org.apache.dubbo.custom.uri.demo.HelloRequest request);

    CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> checkNameAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request);



    /**
         * <pre>
         *  Health check interface that does not use request body
         * </pre>
         */
    org.apache.dubbo.custom.uri.demo.HelloReply simpleCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request);

    CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> simpleCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request);



    /**
         * <pre>
         *  Check interface that supports path variables
         * </pre>
         */
    org.apache.dubbo.custom.uri.demo.HelloReply actionCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request);

    CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> actionCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request);



    /**
         * <pre>
         *  Check interface that maps the name field from request body and supports path variables
         * </pre>
         */
    org.apache.dubbo.custom.uri.demo.HelloReply actionCheckWithName(org.apache.dubbo.custom.uri.demo.HelloRequest request);

    CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> actionCheckWithNameAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request);








}
