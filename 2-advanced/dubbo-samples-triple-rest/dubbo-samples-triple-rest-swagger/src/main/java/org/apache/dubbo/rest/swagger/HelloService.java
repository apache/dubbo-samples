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

package org.apache.dubbo.rest.swagger;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Dubbo Greeting API",
                version = "1.0.0",
                description = "This API provides greeting and health-check services.",
                contact = @Contact(
                        name = "Dubbo Support",
                        email = "support@example.com",
                        url = "https://github.com/apache/dubbo/"

                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        tags = {
                @Tag(name = "HelloService", description = "Provides greeting and health-check operations.")
        }
)
@EnableDubbo
public interface HelloService {
    /**
     * Returns a personalized greeting message based on the user's details.
     *
     * @param user The user object containing details.
     * @return A greeting message.
     */
    @Operation(summary = "Get personalized greeting", description = "Returns a greeting message based on the user's details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned greeting"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    String sayHello(
            @Parameter(description = "The user object containing user details", required = true)
            User user
    );

    /**
     * Checks the health status of the service.
     *
     * @return Health status of the service.
     */
    @Operation(summary = "Health Check", description = "Checks if the service is running properly.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Service is healthy"),
            @ApiResponse(responseCode = "500", description = "Service is down")
    })
    String healthCheck();
}
