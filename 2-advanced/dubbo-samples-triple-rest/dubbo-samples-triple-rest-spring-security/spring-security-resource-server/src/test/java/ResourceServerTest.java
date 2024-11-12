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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.dubbo.rest.demo.ResourceApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ResourceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceServerTest {

    @LocalServerPort
    private int port;

    private final String clientId = "49fd8518-12eb-422b-9264-2bae0ab89f66";
    private final String clientSecret = "H3DTtm2fR3GRAdr4ls1mcg";

    @Test
    public void testGetUserEndpoint() {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // build RestClient request
        RestClient restClient = RestClient.builder().build();
        String url = "http://localhost:" + 9000 + "/oauth2/token";

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

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            String accessToken = jsonNode.get("access_token").asText();

            System.out.println("accessToken: " + accessToken);
            // Use the access token to authenticate the request to the /user endpoint
            assert accessToken != null;
            String userUrl = "http://localhost:" + port + "/api/hello/World";
            try {
                String userResponse = restClient.post()
                        .uri(userUrl)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .retrieve()
                        .body(String.class);

                System.out.println("User Response: " + userResponse);
                assertEquals("Hello, World", userResponse, "The response should be 'Hello,user!'");
            } catch (RestClientResponseException e) {
                System.err.println("Error Response: " + e.getResponseBodyAsString());
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
