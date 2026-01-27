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

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableDubbo
@ExtendWith(SpringExtension.class)
public class ResourceServerIT {

    //    @LocalServerPort
    //    private int port;

//    @DubboReference(url = "tri://localhost:50051")
//    private HelloService helloService;

    private final String clientId = "49fd8518-12eb-422b-9264-2bae0ab89f66";
    private final String clientSecret = "H3DTtm2fR3GRAdr4ls1mcg";

    private static final String OAUTH2HOST = System.getProperty("authorization.address", "localhost");
    private static final String HOST = System.getProperty("resource.address", "localhost");

    @Test
    public void testGetUserEndpoint() {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // build RestClient request
        RestClient restClient = RestClient.builder().build();
        String url = "http://"+ OAUTH2HOST + ":9000/oauth2/token";

        try {
            // make a post request
            String response = restClient.post()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .body("grant_type=client_credentials&scope=read")
                    .retrieve()
                    .body(String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            String accessToken = jsonNode.get("access_token").asText();

            // Use the access token to authenticate the request to the /user endpoint
            assert accessToken != null;
            String userUrl = "http://" + HOST + ":50051/hello/sayHello/World";
            try {
                String userResponse = restClient.get()
                        .uri(userUrl)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .retrieve()
                        .body(String.class);

                assertEquals("\"Hello, World\"", userResponse, "error");
            } catch (RestClientResponseException e) {
                System.err.println("Error Response: " + e.getResponseBodyAsString());
                Assertions.fail("Request failed with response: " + e.getResponseBodyAsString());
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetUserEndpointWithInvalidToken() {
        String invalidAccessToken = "invalid_token";
        RestClient restClient = RestClient.builder().build();
        String userUrl = "http://" + HOST + ":50051/hello/sayHello/World";

        try {
            restClient.get()
                    .uri(userUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidAccessToken)
                    .retrieve()
                    .body(String.class);

            Assertions.fail("Request should have failed with an invalid token");
        } catch (RestClientResponseException e) {
            System.err.println("Error Response: " + e.getResponseBodyAsString());
            assertEquals(401, e.getStatusCode().value(), "Expected 401 Unauthorized status");
        }
    }
}
