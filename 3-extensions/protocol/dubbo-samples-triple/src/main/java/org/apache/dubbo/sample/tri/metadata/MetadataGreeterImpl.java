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

package org.apache.dubbo.sample.tri.metadata;

import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.sample.tri.Greeter;
import org.apache.dubbo.sample.tri.GreeterReply;
import org.apache.dubbo.sample.tri.GreeterRequest;
import org.apache.dubbo.sample.tri.stub.GreeterImpl;

import java.util.concurrent.CompletableFuture;

public class MetadataGreeterImpl implements Greeter {
    private final Greeter delegate;

    public MetadataGreeterImpl() {
        this.delegate = new GreeterImpl("tri-metadata");
    }

    @Override
    public GreeterReply greet(GreeterRequest request) {
        return delegate.greet(request);
    }

    @Override
    public CompletableFuture<GreeterReply> greetAsync(GreeterRequest request) {
        return CompletableFuture.completedFuture(greet(request));
    }

    @Override
    public GreeterReply upperCaseGreet(GreeterRequest request) {
        return delegate.upperCaseGreet(request);
    }

    @Override
    public CompletableFuture<GreeterReply> upperCaseGreetAsync(GreeterRequest request) {
        return CompletableFuture.completedFuture(upperCaseGreet(request));
    }

    @Override
    public GreeterReply greetWithAttachment(GreeterRequest request) {
        return delegate.greetWithAttachment(request);
    }

    @Override
    public CompletableFuture<GreeterReply> greetWithAttachmentAsync(GreeterRequest request) {
        return CompletableFuture.completedFuture(greetWithAttachment(request));
    }

    @Override
    public GreeterReply greetReturnBigAttachment(GreeterRequest request) {
        return delegate.greetReturnBigAttachment(request);
    }

    @Override
    public CompletableFuture<GreeterReply> greetReturnBigAttachmentAsync(GreeterRequest request) {
        return CompletableFuture.completedFuture(greetReturnBigAttachment(request));
    }

    @Override
    public GreeterReply greetException(GreeterRequest request) {
        return delegate.greetException(request);
    }

    @Override
    public CompletableFuture<GreeterReply> greetExceptionAsync(GreeterRequest request) {
        return CompletableFuture.completedFuture(greetException(request));
    }

    @Override
    public GreeterReply queryCancelResult(GreeterRequest request) {
        return delegate.queryCancelResult(request);
    }

    @Override
    public CompletableFuture<GreeterReply> queryCancelResultAsync(GreeterRequest request) {
        return CompletableFuture.completedFuture(queryCancelResult(request));
    }

    @Override
    public void greetServerStream(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        delegate.greetServerStream(request, responseObserver);
    }

    @Override
    public void cancelServerStream(GreeterRequest request, StreamObserver<GreeterReply> responseObserver) {
        delegate.cancelServerStream(request, responseObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> greetStream(StreamObserver<GreeterReply> responseObserver) {
        return delegate.greetStream(responseObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> cancelBiStream(StreamObserver<GreeterReply> responseObserver) {
        return delegate.cancelBiStream(responseObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> cancelBiStream2(StreamObserver<GreeterReply> responseObserver) {
        return delegate.cancelBiStream2(responseObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> compressorBiStream(StreamObserver<GreeterReply> responseObserver) {
        return delegate.compressorBiStream(responseObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> clientCompressorBiStream(StreamObserver<GreeterReply> responseObserver) {
        return delegate.clientCompressorBiStream(responseObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> serverCompressorBiStream(StreamObserver<GreeterReply> responseObserver) {
        return delegate.serverCompressorBiStream(responseObserver);
    }

    @Override
    public StreamObserver<GreeterRequest> greetClientStream(StreamObserver<GreeterReply> responseObserver) {
        return delegate.greetClientStream(responseObserver);
    }
}
