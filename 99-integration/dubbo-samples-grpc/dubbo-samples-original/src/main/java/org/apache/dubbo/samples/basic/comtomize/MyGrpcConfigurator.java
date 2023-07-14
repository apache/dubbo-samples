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
package org.apache.dubbo.samples.basic.comtomize;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.protocol.grpc.interceptors.GrpcConfigurator;

import io.grpc.CallOptions;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Customize the gRPC Server, Channel and CallOptions
 */
public class MyGrpcConfigurator implements GrpcConfigurator {

    private final ExecutorService executor = Executors.newFixedThreadPool(200, new NamedThreadFactory("Customized-grpc", true));

    @Override
    public NettyServerBuilder configureServerBuilder(NettyServerBuilder builder, URL url) {
        return builder.flowControlWindow(1).executor(executor);
    }

    @Override
    public NettyChannelBuilder configureChannelBuilder(NettyChannelBuilder builder, URL url) {
        return builder.directExecutor();
    }

    @Override
    public CallOptions configureCallOptions(CallOptions options, URL url) {
        return options.withOption(CallOptions.Key.create("key"), "value");
    }
}
