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
package org.apache.dubbo.tri.websocket.demo.test;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloClient extends WebSocketClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient.class);

    private final List<String> responses = new ArrayList<>();

    private final CountDownLatch openLatch;

    private int closeCode;

    private String closeMessage;

    public HelloClient(CountDownLatch openLatch, URI serverURI) {
        super(serverURI);
        this.openLatch = openLatch;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LOGGER.info("new connection opened:{}", handshakedata);
        openLatch.countDown();
    }

    @Override
    public void onMessage(String message) {}

    @Override
    public void onMessage(ByteBuffer message) {
        String response = new String(message.array());
        responses.add(response);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LOGGER.info("closed with exit code {}, additional info: {}, remote close:{}", code, reason, remote);
        closeCode = code;
        closeMessage = reason;
    }

    @Override
    public void onError(Exception e) {
        LOGGER.error("WebSocket on error", e);
    }

    public List<String> getResponses() {
        return responses;
    }

    public int getCloseCode() {
        return closeCode;
    }

    public String getCloseMessage() {
        return closeMessage;
    }
}
