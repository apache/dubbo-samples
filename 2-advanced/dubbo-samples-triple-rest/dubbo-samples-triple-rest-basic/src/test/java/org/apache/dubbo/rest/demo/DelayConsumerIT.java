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

package org.apache.dubbo.rest.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpClientTransport;
import org.eclipse.jetty.client.transport.HttpClientConnectionFactory;
import org.eclipse.jetty.client.transport.HttpClientTransportDynamic;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.client.transport.ClientConnectionFactoryOverHTTP2;
import org.eclipse.jetty.io.ClientConnectionFactory;
import org.eclipse.jetty.io.ClientConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;

/**
 * delay to test connection reset issue triggered by first case
 */
@EnableDubbo
@ExtendWith(SpringExtension.class)
public class DelayConsumerIT {

    private static final String HOST = System.getProperty("dubbo.address", "localhost");

    private static final StringBuffer finalString = new StringBuffer();

    static {
        IntStream.range(0, 20000).forEach(i -> finalString.append(i).append("Hello"));
    }

    private static String toUri(String path) {
        return "http://" + HOST + ":50052/org.apache.dubbo.rest.demo.DemoService" + path;
    }

    @BeforeAll
    public static void init() throws InterruptedException {
        System.out.println("delay 2s to test connection reset issue triggered by first case");
        Thread.sleep(2000);
    }

    @Test
    public void helloWithBody() {
        RestClient restClient = RestClient.create();

        User user = new User();
        user.setTitle("Mr");
        user.setName("Yang");

        String result = restClient.post()
                .uri(toUri("/helloUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve()
                .body(String.class);
        Assertions.assertEquals("\"" + finalString + "Hello Mr. Yang\"", result);
    }

    /**
     * The MAX_HEADER_LIST_SIZE value of jetty HTTP/2 client is 8192, which is smaller than the default value of
     * Triple server, Triple server should wait client connection preface before sending back response with large
     * size header, otherwise the client will encounter IOException with invalid_continuation_stream.
     */
    @Test
    public void helloH2cWithExceededLargeSizeHeaderResp() throws Exception {
        ClientConnector clientConnector = new ClientConnector();
        HTTP2Client http2Client = new HTTP2Client(clientConnector);
        ClientConnectionFactory.Info h2 = new ClientConnectionFactoryOverHTTP2.HTTP2(http2Client);
        clientConnector.setSelectors(1);
        QueuedThreadPool clientThreads = new QueuedThreadPool();
        clientThreads.setName("client");
        clientConnector.setExecutor(clientThreads);
        HttpClientTransport httpClientTransport
                = new HttpClientTransportDynamic(clientConnector, HttpClientConnectionFactory.HTTP11, h2);
        HttpClient httpClient = new HttpClient(httpClientTransport);
        httpClient.start();

        try {
            httpClient.newRequest(toUri("/helloH2cWithExceededLargeSizeHeaderResp"))
                    .headers(headers -> headers
                            .put(HttpHeader.UPGRADE, "h2c")
                            .put(HttpHeader.HTTP2_SETTINGS, "")
                            .put(HttpHeader.CONNECTION, "Upgrade, HTTP2-Settings"))
                    .timeout(5, TimeUnit.SECONDS)
                    .send();
            Assertions.fail("Expected exception not occurred!");
        } catch (Exception ex) {
            Assertions.assertEquals("java.io.IOException: cancel_stream_error", ex.getMessage());
        } finally {
            httpClient.stop();
        }
    }
}
