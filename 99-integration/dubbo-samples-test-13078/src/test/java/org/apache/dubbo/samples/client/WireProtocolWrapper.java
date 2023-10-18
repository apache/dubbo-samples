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
package org.apache.dubbo.samples.client;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.api.ProtocolDetector;
import org.apache.dubbo.remoting.api.WireProtocol;
import org.apache.dubbo.remoting.api.pu.ChannelOperator;
import org.apache.dubbo.remoting.api.ssl.ContextOperator;

import io.netty.channel.Channel;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2FrameCodec;
import io.netty.handler.codec.http2.Http2LocalFlowController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WireProtocolWrapper implements WireProtocol {
    private final WireProtocol wireProtocol;
    private final static List<Http2Connection.Endpoint<Http2LocalFlowController>> endpoints = new CopyOnWriteArrayList<>();

    public WireProtocolWrapper(WireProtocol wireProtocol) {
        this.wireProtocol = wireProtocol;
    }

    @Override
    public ProtocolDetector detector() {
        return wireProtocol.detector();
    }

    @Override
    public void configServerProtocolHandler(URL url, ChannelOperator operator) {
        wireProtocol.configServerProtocolHandler(url, operator);
    }

    @Override
    public void configClientPipeline(URL url, ChannelOperator operator, ContextOperator contextOperator) {
        wireProtocol.configClientPipeline(url, operator, contextOperator);
        try {
            Field channelField = operator.getClass().getDeclaredField("channel");
            channelField.setAccessible(true);
            Object dubboChannel = channelField.get(operator);
            Method getNioChannelMethod = dubboChannel.getClass().getDeclaredMethod("getNioChannel");
            getNioChannelMethod.setAccessible(true);
            Channel channel = (Channel)getNioChannelMethod.invoke(dubboChannel);
            Http2FrameCodec http2FrameCodec = channel.pipeline().get(Http2FrameCodec.class);
            Http2Connection.Endpoint<Http2LocalFlowController> endpoint = http2FrameCodec.connection().local();
            endpoints.add(endpoint);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void close() {
        wireProtocol.close();
    }

    public static List<Http2Connection.Endpoint<Http2LocalFlowController>> getEndpoints() {
        return endpoints;
    }
}
