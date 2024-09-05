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
package org.apache.dubbo.rest.demo;

import org.apache.dubbo.rest.demo.pojo.User;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/demo")
public interface DemoService {

    @RequestMapping("/hello")
    String hello(@RequestParam String name);

    @GetMapping(value = "get/param")
    String getParam(@RequestParam String id);

    @GetMapping(value = "get/variable/{id}")
    String getVariable(@PathVariable String id);

    @GetMapping(value = "get/muchParam")
    String getMuchParam(@RequestParam String id, @RequestParam String name);

    @GetMapping(value = "get/muchVariable/{id}/{name}")
    String getMuchVariable(@PathVariable String id, @PathVariable String name);

    @GetMapping(value = "get/reg/{name:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
    String getReg(@PathVariable String name, @PathVariable String version, @PathVariable String ext);

    @PostMapping(value = "post/body")
    String postBody(@RequestBody String name);

    @PostMapping(value = "post/param")
    String postParam(@RequestParam String id);

    @PostMapping(value = "post/variable/{id}")
    String postVariable(@PathVariable String id);

    @PostMapping(value = "post/useConsumes", consumes = MediaType.APPLICATION_JSON_VALUE)
    String postUseConsumes(@RequestBody String id);

    @RequestMapping(value = "/error", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE)
    String error();

    @PostMapping(value = "post/useParams", params = "myParam=myValue")
    String postUseParams(@RequestBody String id);

    @GetMapping(value = "get/head/{id}", headers = "myHeader=myValue")
    String getHead(@PathVariable String id);

    @PostMapping(value = "post/useConsumes/formData", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String postUseConsumesFormData(@RequestBody MultiValueMap<String, String> formData);

    @PostMapping(value = "post/useConsumes/user", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    String postUseConsumesUser(@RequestBody MultiValueMap<String, List<User>> formData);

    // HashMap<String, User> -> JSON
    @PostMapping(value = "post/map/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    String postMapUser(@RequestBody String formData);

    @PutMapping("/put/update/{id}")
    String putUpdateId(@PathVariable String id);

    @DeleteMapping("/delete/{id}")
    String deleteId(@PathVariable String id);

    @PatchMapping("patch/{id}")
    String patchById(@PathVariable String id, @RequestBody String patchData);

    @RequestMapping(method = RequestMethod.GET, value = "/primitive")
    int primitiveInt(@RequestParam("a") int a, @RequestParam("b") int b);

    @RequestMapping(method = RequestMethod.GET, value = "/primitiveLong")
    long primitiveLong(@RequestParam("a") long a, @RequestParam("b") Long b);

    @RequestMapping(method = RequestMethod.GET, value = "/primitiveByte")
    long primitiveByte(@RequestParam("a") byte a, @RequestParam("b") Long b);

    @RequestMapping(method = RequestMethod.POST, value = "/primitiveShort")
    long primitiveShort(@RequestParam("a") short a, @RequestParam("b") Long b, @RequestBody int c);

    @PostMapping(value = "/post/list", consumes = MediaType.ALL_VALUE)
    List<User> postList(@RequestBody List<User> users);

}
