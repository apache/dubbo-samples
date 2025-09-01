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
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.PathResolver;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.ServerService;
import org.apache.dubbo.rpc.TriRpcStatus;
import org.apache.dubbo.rpc.model.MethodDescriptor;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.StubMethodDescriptor;
import org.apache.dubbo.rpc.model.StubServiceDescriptor;
import org.apache.dubbo.rpc.service.Destroyable;
import org.apache.dubbo.rpc.stub.BiStreamMethodHandler;
import org.apache.dubbo.rpc.stub.ServerStreamMethodHandler;
import org.apache.dubbo.rpc.stub.StubInvocationUtil;
import org.apache.dubbo.rpc.stub.StubInvoker;
import org.apache.dubbo.rpc.stub.StubMethodHandler;
import org.apache.dubbo.rpc.stub.StubSuppliers;
import org.apache.dubbo.rpc.stub.UnaryStubMethodHandler;

import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.concurrent.CompletableFuture;

public final class DubboGreeterTriple {

    public static final String SERVICE_NAME = Greeter.SERVICE_NAME;

    private static final StubServiceDescriptor serviceDescriptor = new StubServiceDescriptor(SERVICE_NAME,Greeter.class);

    static {
        org.apache.dubbo.rpc.protocol.tri.service.SchemaDescriptorRegistry.addSchemaDescriptor(SERVICE_NAME,HelloWorldProto.getDescriptor());
        StubSuppliers.addSupplier(SERVICE_NAME, DubboGreeterTriple::newStub);
        StubSuppliers.addSupplier(Greeter.JAVA_SERVICE_NAME,  DubboGreeterTriple::newStub);
        StubSuppliers.addDescriptor(SERVICE_NAME, serviceDescriptor);
        StubSuppliers.addDescriptor(Greeter.JAVA_SERVICE_NAME, serviceDescriptor);
    }

    @SuppressWarnings("all")
    public static Greeter newStub(Invoker<?> invoker) {
        return new GreeterStub((Invoker<Greeter>)invoker);
    }

    /**
         * <pre>
         *  User&#39;s name
         * </pre>
         */
    private static final StubMethodDescriptor sayHelloWithPostMethod = new StubMethodDescriptor("SayHelloWithPost",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor sayHelloWithPostAsyncMethod = new StubMethodDescriptor("SayHelloWithPost",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor sayHelloWithPostProxyAsyncMethod = new StubMethodDescriptor("SayHelloWithPostAsync",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);
    /**
         * <pre>
         *  Update the greeting using PUT method
         * </pre>
         */
    private static final StubMethodDescriptor updateGreetingMethod = new StubMethodDescriptor("UpdateGreeting",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor updateGreetingAsyncMethod = new StubMethodDescriptor("UpdateGreeting",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor updateGreetingProxyAsyncMethod = new StubMethodDescriptor("UpdateGreetingAsync",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);
    /**
         * <pre>
         *  Health check interface, maps request body to HelloRequest
         * </pre>
         */
    private static final StubMethodDescriptor healthCheckMethod = new StubMethodDescriptor("HealthCheck",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor healthCheckAsyncMethod = new StubMethodDescriptor("HealthCheck",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor healthCheckProxyAsyncMethod = new StubMethodDescriptor("HealthCheckAsync",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);
    /**
         * <pre>
         *  Health check interface
         * </pre>
         */
    private static final StubMethodDescriptor checkNameMethod = new StubMethodDescriptor("CheckName",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor checkNameAsyncMethod = new StubMethodDescriptor("CheckName",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor checkNameProxyAsyncMethod = new StubMethodDescriptor("CheckNameAsync",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);
    /**
         * <pre>
         *  Health check interface that does not use request body
         * </pre>
         */
    private static final StubMethodDescriptor simpleCheckMethod = new StubMethodDescriptor("SimpleCheck",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor simpleCheckAsyncMethod = new StubMethodDescriptor("SimpleCheck",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor simpleCheckProxyAsyncMethod = new StubMethodDescriptor("SimpleCheckAsync",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);
    /**
         * <pre>
         *  Check interface that supports path variables
         * </pre>
         */
    private static final StubMethodDescriptor actionCheckMethod = new StubMethodDescriptor("ActionCheck",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor actionCheckAsyncMethod = new StubMethodDescriptor("ActionCheck",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor actionCheckProxyAsyncMethod = new StubMethodDescriptor("ActionCheckAsync",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);
    /**
         * <pre>
         *  Check interface that maps the name field from request body and supports path variables
         * </pre>
         */
    private static final StubMethodDescriptor actionCheckWithNameMethod = new StubMethodDescriptor("ActionCheckWithName",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor actionCheckWithNameAsyncMethod = new StubMethodDescriptor("ActionCheckWithName",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, java.util.concurrent.CompletableFuture.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);

    private static final StubMethodDescriptor actionCheckWithNameProxyAsyncMethod = new StubMethodDescriptor("ActionCheckWithNameAsync",
    org.apache.dubbo.custom.uri.demo.HelloRequest.class, org.apache.dubbo.custom.uri.demo.HelloReply.class, MethodDescriptor.RpcType.UNARY,
    obj -> ((Message) obj).toByteArray(), obj -> ((Message) obj).toByteArray(), org.apache.dubbo.custom.uri.demo.HelloRequest::parseFrom,
    org.apache.dubbo.custom.uri.demo.HelloReply::parseFrom);




    static{
        serviceDescriptor.addMethod(sayHelloWithPostMethod);
        serviceDescriptor.addMethod(sayHelloWithPostProxyAsyncMethod);
        serviceDescriptor.addMethod(updateGreetingMethod);
        serviceDescriptor.addMethod(updateGreetingProxyAsyncMethod);
        serviceDescriptor.addMethod(healthCheckMethod);
        serviceDescriptor.addMethod(healthCheckProxyAsyncMethod);
        serviceDescriptor.addMethod(checkNameMethod);
        serviceDescriptor.addMethod(checkNameProxyAsyncMethod);
        serviceDescriptor.addMethod(simpleCheckMethod);
        serviceDescriptor.addMethod(simpleCheckProxyAsyncMethod);
        serviceDescriptor.addMethod(actionCheckMethod);
        serviceDescriptor.addMethod(actionCheckProxyAsyncMethod);
        serviceDescriptor.addMethod(actionCheckWithNameMethod);
        serviceDescriptor.addMethod(actionCheckWithNameProxyAsyncMethod);
    }

    public static class GreeterStub implements Greeter, Destroyable {
        private final Invoker<Greeter> invoker;

        public GreeterStub(Invoker<Greeter> invoker) {
            this.invoker = invoker;
        }

        @Override
        public void $destroy() {
              invoker.destroy();
         }

        /**
         * <pre>
         *  User&#39;s name
         * </pre>
         */
        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply sayHelloWithPost(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, sayHelloWithPostMethod, request);
        }

        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> sayHelloWithPostAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, sayHelloWithPostAsyncMethod, request);
        }

        /**
         * <pre>
         *  User&#39;s name
         * </pre>
         */
        public void sayHelloWithPost(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, sayHelloWithPostMethod , request, responseObserver);
        }
        /**
         * <pre>
         *  Update the greeting using PUT method
         * </pre>
         */
        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply updateGreeting(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, updateGreetingMethod, request);
        }

        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> updateGreetingAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, updateGreetingAsyncMethod, request);
        }

        /**
         * <pre>
         *  Update the greeting using PUT method
         * </pre>
         */
        public void updateGreeting(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, updateGreetingMethod , request, responseObserver);
        }
        /**
         * <pre>
         *  Health check interface, maps request body to HelloRequest
         * </pre>
         */
        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply healthCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, healthCheckMethod, request);
        }

        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> healthCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, healthCheckAsyncMethod, request);
        }

        /**
         * <pre>
         *  Health check interface, maps request body to HelloRequest
         * </pre>
         */
        public void healthCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, healthCheckMethod , request, responseObserver);
        }
        /**
         * <pre>
         *  Health check interface
         * </pre>
         */
        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply checkName(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, checkNameMethod, request);
        }

        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> checkNameAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, checkNameAsyncMethod, request);
        }

        /**
         * <pre>
         *  Health check interface
         * </pre>
         */
        public void checkName(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, checkNameMethod , request, responseObserver);
        }
        /**
         * <pre>
         *  Health check interface that does not use request body
         * </pre>
         */
        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply simpleCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, simpleCheckMethod, request);
        }

        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> simpleCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, simpleCheckAsyncMethod, request);
        }

        /**
         * <pre>
         *  Health check interface that does not use request body
         * </pre>
         */
        public void simpleCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, simpleCheckMethod , request, responseObserver);
        }
        /**
         * <pre>
         *  Check interface that supports path variables
         * </pre>
         */
        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply actionCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, actionCheckMethod, request);
        }

        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> actionCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, actionCheckAsyncMethod, request);
        }

        /**
         * <pre>
         *  Check interface that supports path variables
         * </pre>
         */
        public void actionCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, actionCheckMethod , request, responseObserver);
        }
        /**
         * <pre>
         *  Check interface that maps the name field from request body and supports path variables
         * </pre>
         */
        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply actionCheckWithName(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, actionCheckWithNameMethod, request);
        }

        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> actionCheckWithNameAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            return StubInvocationUtil.unaryCall(invoker, actionCheckWithNameAsyncMethod, request);
        }

        /**
         * <pre>
         *  Check interface that maps the name field from request body and supports path variables
         * </pre>
         */
        public void actionCheckWithName(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            StubInvocationUtil.unaryCall(invoker, actionCheckWithNameMethod , request, responseObserver);
        }



    }

    public static abstract class GreeterImplBase implements Greeter, ServerService<Greeter> {

        private <T, R> BiConsumer<T, StreamObserver<R>> syncToAsync(java.util.function.Function<T, R> syncFun) {
            return new BiConsumer<T, StreamObserver<R>>() {
                @Override
                public void accept(T t, StreamObserver<R> observer) {
                    try {
                        R ret = syncFun.apply(t);
                        observer.onNext(ret);
                        observer.onCompleted();
                    } catch (Throwable e) {
                        observer.onError(e);
                    }
                }
            };
        }

        @Override
        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> sayHelloWithPostAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
                return CompletableFuture.completedFuture(sayHelloWithPost(request));
        }
        @Override
        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> updateGreetingAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
                return CompletableFuture.completedFuture(updateGreeting(request));
        }
        @Override
        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> healthCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
                return CompletableFuture.completedFuture(healthCheck(request));
        }
        @Override
        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> checkNameAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
                return CompletableFuture.completedFuture(checkName(request));
        }
        @Override
        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> simpleCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
                return CompletableFuture.completedFuture(simpleCheck(request));
        }
        @Override
        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> actionCheckAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
                return CompletableFuture.completedFuture(actionCheck(request));
        }
        @Override
        public CompletableFuture<org.apache.dubbo.custom.uri.demo.HelloReply> actionCheckWithNameAsync(org.apache.dubbo.custom.uri.demo.HelloRequest request){
                return CompletableFuture.completedFuture(actionCheckWithName(request));
        }

        /**
        * This server stream type unary method is <b>only</b> used for generated stub to support async unary method.
        * It will not be called if you are NOT using Dubbo3 generated triple stub and <b>DO NOT</b> implement this method.
        */
        public void sayHelloWithPost(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            sayHelloWithPostAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }
        public void updateGreeting(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            updateGreetingAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }
        public void healthCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            healthCheckAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }
        public void checkName(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            checkNameAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }
        public void simpleCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            simpleCheckAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }
        public void actionCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            actionCheckAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }
        public void actionCheckWithName(org.apache.dubbo.custom.uri.demo.HelloRequest request, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply> responseObserver){
            actionCheckWithNameAsync(request).whenComplete((r, t) -> {
                if (t != null) {
                    responseObserver.onError(t);
                } else {
                    responseObserver.onNext(r);
                    responseObserver.onCompleted();
                }
            });
        }

        @Override
        public final Invoker<Greeter> getInvoker(URL url) {
            PathResolver pathResolver = url.getOrDefaultFrameworkModel()
            .getExtensionLoader(PathResolver.class)
            .getDefaultExtension();
            Map<String,StubMethodHandler<?, ?>> handlers = new HashMap<>();

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/SayHelloWithPost");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/SayHelloWithPostAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/SayHelloWithPost");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/SayHelloWithPostAsync");

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/UpdateGreeting");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/UpdateGreetingAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/UpdateGreeting");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/UpdateGreetingAsync");

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/HealthCheck");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/HealthCheckAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/HealthCheck");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/HealthCheckAsync");

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/CheckName");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/CheckNameAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/CheckName");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/CheckNameAsync");

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/SimpleCheck");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/SimpleCheckAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/SimpleCheck");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/SimpleCheckAsync");

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/ActionCheck");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/ActionCheckAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/ActionCheck");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/ActionCheckAsync");

            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/ActionCheckWithName");
            pathResolver.addNativeStub( "/" + SERVICE_NAME + "/ActionCheckWithNameAsync");
            // for compatibility
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/ActionCheckWithName");
            pathResolver.addNativeStub( "/" + JAVA_SERVICE_NAME + "/ActionCheckWithNameAsync");


            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> sayHelloWithPostFunc = this::sayHelloWithPost;
            handlers.put(sayHelloWithPostMethod.getMethodName(), new UnaryStubMethodHandler<>(sayHelloWithPostFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> sayHelloWithPostAsyncFunc = syncToAsync(this::sayHelloWithPost);
            handlers.put(sayHelloWithPostProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(sayHelloWithPostAsyncFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> updateGreetingFunc = this::updateGreeting;
            handlers.put(updateGreetingMethod.getMethodName(), new UnaryStubMethodHandler<>(updateGreetingFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> updateGreetingAsyncFunc = syncToAsync(this::updateGreeting);
            handlers.put(updateGreetingProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(updateGreetingAsyncFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> healthCheckFunc = this::healthCheck;
            handlers.put(healthCheckMethod.getMethodName(), new UnaryStubMethodHandler<>(healthCheckFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> healthCheckAsyncFunc = syncToAsync(this::healthCheck);
            handlers.put(healthCheckProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(healthCheckAsyncFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> checkNameFunc = this::checkName;
            handlers.put(checkNameMethod.getMethodName(), new UnaryStubMethodHandler<>(checkNameFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> checkNameAsyncFunc = syncToAsync(this::checkName);
            handlers.put(checkNameProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(checkNameAsyncFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> simpleCheckFunc = this::simpleCheck;
            handlers.put(simpleCheckMethod.getMethodName(), new UnaryStubMethodHandler<>(simpleCheckFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> simpleCheckAsyncFunc = syncToAsync(this::simpleCheck);
            handlers.put(simpleCheckProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(simpleCheckAsyncFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> actionCheckFunc = this::actionCheck;
            handlers.put(actionCheckMethod.getMethodName(), new UnaryStubMethodHandler<>(actionCheckFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> actionCheckAsyncFunc = syncToAsync(this::actionCheck);
            handlers.put(actionCheckProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(actionCheckAsyncFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> actionCheckWithNameFunc = this::actionCheckWithName;
            handlers.put(actionCheckWithNameMethod.getMethodName(), new UnaryStubMethodHandler<>(actionCheckWithNameFunc));
            BiConsumer<org.apache.dubbo.custom.uri.demo.HelloRequest, StreamObserver<org.apache.dubbo.custom.uri.demo.HelloReply>> actionCheckWithNameAsyncFunc = syncToAsync(this::actionCheckWithName);
            handlers.put(actionCheckWithNameProxyAsyncMethod.getMethodName(), new UnaryStubMethodHandler<>(actionCheckWithNameAsyncFunc));




            return new StubInvoker<>(this, url, Greeter.class, handlers);
        }


        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply sayHelloWithPost(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            throw unimplementedMethodException(sayHelloWithPostMethod);
        }

        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply updateGreeting(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            throw unimplementedMethodException(updateGreetingMethod);
        }

        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply healthCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            throw unimplementedMethodException(healthCheckMethod);
        }

        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply checkName(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            throw unimplementedMethodException(checkNameMethod);
        }

        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply simpleCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            throw unimplementedMethodException(simpleCheckMethod);
        }

        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply actionCheck(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            throw unimplementedMethodException(actionCheckMethod);
        }

        @Override
        public org.apache.dubbo.custom.uri.demo.HelloReply actionCheckWithName(org.apache.dubbo.custom.uri.demo.HelloRequest request){
            throw unimplementedMethodException(actionCheckWithNameMethod);
        }





        @Override
        public final ServiceDescriptor getServiceDescriptor() {
            return serviceDescriptor;
        }
        private RpcException unimplementedMethodException(StubMethodDescriptor methodDescriptor) {
            return TriRpcStatus.UNIMPLEMENTED.withDescription(String.format("Method %s is unimplemented",
                "/" + serviceDescriptor.getInterfaceName() + "/" + methodDescriptor.getMethodName())).asException();
        }
    }

}
