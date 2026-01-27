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

package org.apache.dubbo.rest.demo.api.provider;

import org.apache.dubbo.rest.demo.model.Error;
import org.apache.dubbo.rest.demo.model.HelloRequest;
import org.apache.dubbo.rest.demo.model.User;
import org.apache.dubbo.rest.demo.model.*;
import org.apache.dubbo.rest.demo.api.interfaces.DemoService;
import java.util.List;
import java.util.Map;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Generated;


@Generated(value = "org.openapitools.codegen.languages.JavaDubboServerCodegen", comments = "Generator version: 7.16.0-SNAPSHOT")

@DubboService
public class DemoServiceImpl implements DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String hello(
        HelloRequest helloRequest
    ) {
        logger.info("Dubbo service method hello called with parameters: helloRequest={}", helloRequest);
        
        // TODO: Implement your business logic here
        // Replace this with actual implementation
        return null;
    }

    @Override
    public String helloUser(
        User user
    ) {
        logger.info("Dubbo service method helloUser called with parameters: user={}", user);
        
        // TODO: Implement your business logic here
        // Replace this with actual implementation
        return null;
    }
}
