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
package org.apache.dubbo.samples.microservices.sc.rest;

import org.apache.dubbo.samples.microservices.sc.rest.model.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Usually, this feign-annotated interface is copied from a Spring Cloud client application.
 * So, for Dubbo to work as a Spring Cloud consumer, you don't have to write a new interface(the service), just copy from Spring Cloud application as is.
 */

@FeignClient(name = "spring-cloud-provider-for-dubbo")//TODO, better if this annotation can automatically match to provided-by in Dubbo
@Controller // FIXME, controller annotation would not be mandatory after FeignClient being supported in dubbo-3.3.0 release.
public interface UserServiceFeign {

    //FIXME, produces should be application/json by default
    @RequestMapping(value="/users/list", method = RequestMethod.GET, produces = "application/json")
    List<User> users();

}
