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
import java.util.HashMap;
import java.util.List;

@RequestMapping("/demo")
public interface DemoService {

    @GetMapping(value = "get/param")
    public String getParam(@RequestParam(value = "id")String id);

    @GetMapping(value = "get/variable/{id}")
    public String getVariable(@PathVariable(value = "id")String id);

    @GetMapping(value = "get/muchParam")
    public String getMuchParam(@RequestParam(value = "id")String id, @RequestParam(value = "name")String name);

    @GetMapping(value = "get/muchVariable/{id}/{name}")
    public String getMuchVariable(@PathVariable(value = "id")String id, @PathVariable(value = "name")String name);

    @GetMapping(value = "get/reg/{name:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
    public String getReg(@PathVariable(value = "name")String name, @PathVariable(value = "version")String version, @PathVariable(value = "ext")String ext);

    @PostMapping(value = "post/body")
    public String postBody(@RequestBody String name);

    @PostMapping(value = "post/param")
    public String postParam(@RequestParam(value = "id")String id);

    @PostMapping(value = "post/variable/{id}")
    public String postVariable(@PathVariable(value = "id")String id);

    @PostMapping(value = "post/useConsumes", consumes = "application/json")
    public String postUseConsumes(@RequestBody String id);

    @RequestMapping(value = "/error", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE)
    String error();

    @PostMapping(value = "post/useParams", params = "myParam=myValue")
    public String postUseParams(@RequestBody String id);

    @GetMapping(value = "get/head/{id}", headers = "myHeader=myValue")
    public String getHead(@PathVariable(value = "id")String id);

    @PostMapping(value = "post/useConsumes/formData", consumes = "application/x-www-form-urlencoded")
    // 使用MultiValueMap 会显示反序列化失败 ？
    // 但是使用hashmap又不支持 因为声明为 application/x-www-form-urlencoded
    public String postUseConsumesFormData(@RequestBody MultiValueMap<String, String> formData);

    // content type为"application/x-www-form-urlencoded"时body的类型必须是MultiValueMap类型
    @PostMapping(value = "post/useConsumes/user", consumes = "application/x-www-form-urlencoded", produces = MediaType.TEXT_PLAIN_VALUE)
    String postUseConsumesUser(@RequestBody MultiValueMap<String, List<User>> formData);

    // HashMap<String, User> -> JSON
    @PostMapping(value = "post/map/user", consumes = "application/json")
    public String postMapUser(@RequestBody String formData);

    @PutMapping("/put/update/{id}")
    public String putUpdateId(@PathVariable String id);

    @DeleteMapping("/delete/{id}")
    public String deleteId(@PathVariable(value = "id")String id);

    @PatchMapping("patch/{id}")
    public String patchById(@PathVariable String id, @RequestBody String patchData);

    @RequestMapping(method = RequestMethod.GET, value = "/primitive")
    int primitiveInt(@RequestParam("a") int a, @RequestParam("b") int b);

    @RequestMapping(method = RequestMethod.GET, value = "/primitiveLong")
    long primitiveLong(@RequestParam("a") long a, @RequestParam("b") Long b);

    @RequestMapping(method = RequestMethod.GET, value = "/primitiveByte")
    long primitiveByte(@RequestParam("a") byte a, @RequestParam("b") Long b);

    @RequestMapping(method = RequestMethod.POST, value = "/primitiveShort")
    long primitiveShort(@RequestParam("a") short a, @RequestParam("b") Long b, @RequestBody int c);

    @PostMapping(value = "/post/list", consumes = MediaType.ALL_VALUE)
    public List<User> postList(@RequestBody List<User> users);


}
