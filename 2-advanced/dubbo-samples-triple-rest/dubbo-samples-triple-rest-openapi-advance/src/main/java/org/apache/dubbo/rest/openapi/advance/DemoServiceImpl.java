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

package org.apache.dubbo.rest.openapi.advance;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.remoting.http12.rest.OpenAPI;
import org.apache.dubbo.remoting.http12.rest.Operation;
import org.apache.dubbo.remoting.http12.rest.Schema;

@DubboService
@OpenAPI(infoTitle = "Dubbo OpenAPI", infoDescription = "This API provides greeting services for users.", infoVersion = "v1", docDescription = "API for greeting users with their names and titles.")
public class DemoServiceImpl implements DemoService {

    @Operation(description = "Returns a greeting message with user's title and name, along with a count.")
    @Override
    public String hello(
            @Schema(description = "User object containing title and name") User user,
            @Schema(description = "Number of times to greet") int count) {
        return "Hello " + user.getTitle() + ". " + user.getName() + ", " + count;
    }

    @Operation(description = "Returns a greeting message with user's title and name.")
    @Override
    public String helloUser(@Schema(description = "User object containing title and name") User user) {
        return "Hello " + user.getTitle() + ". " + user.getName();
    }
}
