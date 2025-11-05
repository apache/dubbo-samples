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
package org.apache.dubbo.registry.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.registry.DemoService;
import org.apache.dubbo.registry.HelloRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WebController {
    @DubboReference
    private DemoService demoService;

    public WebController() {
        System.out.println("test");
    }

    // user GET to avoid resubmit warning on browser side.
    @RequestMapping(value = "/dubbo-sae", method = RequestMethod.GET)
    @ResponseBody
    String test() {
        return "Here is the response from provider: \"" + demoService.sayHello(new HelloRequest("sae")) + "\".";
    }
}