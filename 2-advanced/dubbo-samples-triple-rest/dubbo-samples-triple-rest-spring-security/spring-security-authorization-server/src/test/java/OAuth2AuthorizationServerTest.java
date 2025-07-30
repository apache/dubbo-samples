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

import org.apache.dubbo.rest.demo.AuthorizationApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = AuthorizationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OAuth2AuthorizationServerTest {

    @LocalServerPort
    private int port;

    private final String clientId = "49fd8518-12eb-422b-9264-2bae0ab89f66";
    private final String clientSecret = "H3DTtm2fR3GRAdr4ls1mcg";

    @Test
    public void testClientCredentialsGrantFlow() {
        assertNotEquals(0, port, "Port should not be 0");
        // build Basic Auth header
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        System.out.println("Encoded Credentials: " + encodedCredentials);

        // build RestClient request
        RestClient restClient = RestClient.builder().build();
        String url = "http://localhost:" + port + "/oauth2/token";

        try {
            // make a post request
            String response = restClient.post()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .body("grant_type=client_credentials&scope=read")
                    .retrieve()
                    .body(String.class);

            System.out.println("Access Token Response: " + response);

        } catch (RestClientResponseException e) {
            // use getStatusCode().value() to get status code
            assertEquals(HttpStatus.UNAUTHORIZED.value(), e.getStatusCode()
                    .value(), "The request failed and was not authorized");
            System.err.println("Error Response: " + e.getResponseBodyAsString());
        }
    }
}
