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

package org.apache.dubbo.rest.demo.api.consumer;

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
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Generated;


@Generated(value = "org.openapitools.codegen.languages.JavaDubboServerCodegen", comments = "Generator version: 7.16.0-SNAPSHOT")

@RestController
@RequestMapping("/DemoService")
public class DemoServiceController {

    @DubboReference
    private DemoService demoService;

    @RequestMapping(method = RequestMethod.POST, value = "/hello")
    public String hello(
        @RequestParam(name = "helloRequest") HelloRequest helloRequest
    ) {
        return demoService.hello(helloRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/helloUser")
    public String helloUser(
        @RequestParam(name = "user") User user
    ) {
        return demoService.helloUser(user);
    }
}
