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

import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
public class WebSocketWithTomcatIT {

    private final String tomcatAddress = System.getProperty("dubbo.address", "localhost") + ":8080";

    @Test
    public void testHelloWithTomcat() throws URISyntaxException, InterruptedException {
        HelloClient helloClient = new HelloClient(
                new URI("ws://" + tomcatAddress + "/org.apache.dubbo.tri.websocket.demo.DemoService/sayHello"));
        helloClient.connectBlocking();
        helloClient.send("{\"world\": 1}");
        TimeUnit.SECONDS.sleep(1);
        List<String> responses = helloClient.getResponses();
        Assertions.assertEquals(1, responses.size());
        Assertions.assertEquals("\"Hello, {\\\"world\\\":1}\"", responses.get(0));
        helloClient.close();
    }

    @Test
    public void testHelloErrorWithTomcat() throws URISyntaxException, InterruptedException {
        HelloClient helloClient = new HelloClient(
                new URI("ws://" + tomcatAddress + "/org.apache.dubbo.tri.websocket.demo.DemoService/sayHelloError"));
        helloClient.connectBlocking();
        helloClient.send("{\"world\": 1}");
        TimeUnit.SECONDS.sleep(1);
        List<String> responses = helloClient.getResponses();
        Assertions.assertEquals(0, responses.size());
        Assertions.assertEquals(WebSocketCloseStatus.INTERNAL_SERVER_ERROR.code(), helloClient.getCloseCode());
        Assertions.assertEquals("test error: {\"world\":1}", helloClient.getCloseMessage());
        helloClient.close();
    }

    @Test
    public void testServerStreamWithTomcat() throws URISyntaxException, InterruptedException {
        HelloClient helloClient = new HelloClient(new URI(
                "ws://" + tomcatAddress + "/org.apache.dubbo.tri.websocket.demo.DemoService/greetServerStream"));
        helloClient.connectBlocking();
        helloClient.send("{\"world\": 1}");
        TimeUnit.SECONDS.sleep(1);
        List<String> responses = helloClient.getResponses();
        Assertions.assertEquals(10, responses.size());
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals("\"Hello, {\\\"world\\\":1}" + i + "\"", responses.get(i));
        }
        helloClient.close();
    }

    @Test
    public void testServerStreamErrorWithTomcat() throws URISyntaxException, InterruptedException {
        HelloClient helloClient = new HelloClient(new URI(
                "ws://" + tomcatAddress + "/org.apache.dubbo.tri.websocket.demo.DemoService/greetServerStreamError"));
        helloClient.connectBlocking();
        helloClient.send("{\"world\": 1}");
        TimeUnit.SECONDS.sleep(1);
        List<String> responses = helloClient.getResponses();
        Assertions.assertEquals(0, responses.size());
        Assertions.assertEquals(WebSocketCloseStatus.INTERNAL_SERVER_ERROR.code(), helloClient.getCloseCode());
        Assertions.assertEquals("test error: {\"world\":1}", helloClient.getCloseMessage());
        helloClient.close();
    }

    @Test
    public void testServerStreamDirectErrorWithTomcat() throws URISyntaxException, InterruptedException {
        HelloClient helloClient = new HelloClient(new URI("ws://" + tomcatAddress
                + "/org.apache.dubbo.tri.websocket.demo.DemoService/greetServerStreamDirectError"));
        helloClient.connectBlocking();
        helloClient.send("{\"world\": 1}");
        TimeUnit.SECONDS.sleep(1);
        List<String> responses = helloClient.getResponses();
        Assertions.assertEquals(0, responses.size());
        Assertions.assertEquals(WebSocketCloseStatus.INTERNAL_SERVER_ERROR.code(), helloClient.getCloseCode());
        Assertions.assertEquals("test direct error: {\"world\":1}", helloClient.getCloseMessage());
        helloClient.close();
    }

    @Test
    public void testBiStreamWithTomcat() throws URISyntaxException, InterruptedException {
        HelloClient helloClient = new HelloClient(
                new URI("ws://" + tomcatAddress + "/org.apache.dubbo.tri.websocket.demo.DemoService/greetBiStream"));
        helloClient.connectBlocking();
        for (int i = 0; i < 10; i++) {
            helloClient.send("{\"world\": " + i + "}");
        }
        TimeUnit.SECONDS.sleep(1);
        List<String> responses = helloClient.getResponses();
        Assertions.assertEquals(10, responses.size());
        for (int i = 0; i < 10; i++) {
            Assertions.assertEquals("\"Hello, {\\\"world\\\":" + i + "}\"", responses.get(i));
        }

        for (int i = 10; i < 20; i++) {
            helloClient.send("{\"world\": " + i + "}");
        }
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(20, responses.size());
        for (int i = 10; i < 20; i++) {
            Assertions.assertEquals("\"Hello, {\\\"world\\\":" + i + "}\"", responses.get(i));
        }

        helloClient.close();
    }

    @Test
    public void testBiStreamErrorWithTomcat() throws URISyntaxException, InterruptedException {
        HelloClient helloClient = new HelloClient(new URI(
                "ws://" + tomcatAddress + "/org.apache.dubbo.tri.websocket.demo.DemoService/greetBiStreamError"));
        helloClient.connectBlocking();
        for (int i = 0; i < 10; i++) {
            helloClient.send("{\"world\": " + i + "}");
        }
        TimeUnit.SECONDS.sleep(1);
        List<String> responses = helloClient.getResponses();
        Assertions.assertEquals(0, responses.size());
        Assertions.assertEquals(WebSocketCloseStatus.INTERNAL_SERVER_ERROR.code(), helloClient.getCloseCode());
        Assertions.assertEquals("test error: {\"world\":0}", helloClient.getCloseMessage());
        helloClient.close();
    }

    @Test
    public void testBiStreamDirectErrorWithTomcat() throws URISyntaxException, InterruptedException {
        HelloClient helloClient = new HelloClient(new URI(
                "ws://" + tomcatAddress + "/org.apache.dubbo.tri.websocket.demo.DemoService/greetBiStreamDirectError"));
        helloClient.connectBlocking();
        for (int i = 0; i < 10; i++) {
            helloClient.send("{\"world\": " + i + "}");
        }
        TimeUnit.SECONDS.sleep(1);
        List<String> responses = helloClient.getResponses();
        Assertions.assertEquals(0, responses.size());
        Assertions.assertEquals(WebSocketCloseStatus.INTERNAL_SERVER_ERROR.code(), helloClient.getCloseCode());
        Assertions.assertEquals("test direct error: {\"world\":0}", helloClient.getCloseMessage());
        helloClient.close();
    }
}
