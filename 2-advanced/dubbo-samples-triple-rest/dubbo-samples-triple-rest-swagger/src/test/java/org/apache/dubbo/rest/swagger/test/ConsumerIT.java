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

package org.apache.dubbo.rest.swagger.test;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

@SpringBootTest
public class ConsumerIT {

    private static final String HOST = System.getProperty("dubbo.address", "localhost");
    private final RestClient webClient = RestClient.create();

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private static String toUri(String path) {
        return "http://" + HOST + ":50052/" + path;
    }

    @Test
    public void testOpenApiDocumentation() {
        // Request OpenAPI documentation
        String result = webClient.get()
                .uri(toUri("dubbo/openapi/api-docs"))
                .retrieve()
                .body(String.class);

        // Assert response is not null
        Assert.assertNotNull("OpenAPI documentation response should not be null", result);

        // Validate expected endpoints in OpenAPI
        assertContains(result, "/org.apache.dubbo.rest.swagger.HelloService/healthCheck");
        assertContains(result, "/org.apache.dubbo.rest.swagger.HelloService/sayHello");
    }

    // Utility method to assert a substring exists in a string
    private void assertContains(String haystack, String needle) {
        Assert.assertTrue("Expected substring not found: " + needle, haystack.contains(needle));
    }
}
