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
package org.apache.dubbo.rest.demo.provider;

import com.alibaba.fastjson2.TypeReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rest.demo.DemoService;
import org.apache.dubbo.rest.demo.pojo.User;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

@DubboService
public class DemoServiceImpl implements DemoService {
    private static final String prefix = "hello ";

    @Override
    public String getParam(String id) {
        return prefix + id;
    }

    @Override
    public String getVariable(String id) {
        return prefix + id;
    }

    @Override
    public String getMuchParam(String id, String name) {
        return prefix + id + " " + name;
    }

    @Override
    public String getMuchVariable(String id, String name) {
        return prefix + id + " " + name;
    }

    @Override
    public String getReg(String name, String version, String ext) {
        return prefix + name + " " +  version + " " + ext;
    }

    @Override
    public String postBody(String name_json) {
        JSONObject jsonObject = new JSONObject(name_json);
        return prefix + jsonObject.getString("name");
    }

    @Override
    public String postParam(String id) {
        return prefix + id;
    }

    @Override
    public String postVariable(String id) {
        return prefix + id;
    }

    @Override
    public String postUseConsumes(String id) {
        JSONObject jsonObject = new JSONObject(id);
        return prefix + jsonObject.getString("id");
    }

    @Override
    public String postUseParams(String id) {
        JSONObject jsonObject = new JSONObject(id);
        return prefix + jsonObject.getString("id");
    }

    @Override
    public String getHead(String id) {
        return prefix + id;
    }

    @Override
    public String postUseConsumesFormData(MultiValueMap<String, String> formData) {
        return prefix + formData.get("id").get(0) + " " + formData.get("name").get(0);
    }

    @Override
    public String postMapUser(String formData) {
        HashMap<String, User> userHashMap = com.alibaba.fastjson2.JSONObject.parseObject(formData, new TypeReference<HashMap<String, User>>() {
            @Override
            public HashMap<String, User> parseObject(String text) {
                return super.parseObject(text);
            }
        });

        return prefix + userHashMap.get("user1").getId() + " " + userHashMap.get("user2").getId();
    }

    @Override
    public String putUpdateId(@PathVariable String id){
        return prefix + id;
    }

    @Override
    public String deleteId(@PathVariable(value = "id")String id){
        return prefix + id;
    }

    @Override
    public String patchById(@PathVariable String id, @RequestBody String patchData){
        JSONObject jsonObject = new JSONObject(patchData);
        String name = jsonObject.getString("name");

        return prefix + id + " " + name;
    }

    @Override
    public String error() {
        throw new RuntimeException();
    }

    @Override
    public String postUseConsumesUser(@RequestBody MultiValueMap<String, List<User>> formData){
        return prefix + formData.get("user1").get(0).get(0).getName();
    }

    @Override
    public int primitiveInt(int a, int b) {
        return a + b;
    }

    @Override
    public long primitiveLong(long a, Long b) {
        return a + b;
    }

    @Override
    public long primitiveByte(byte a, Long b) {
        return a + b;
    }

    @Override
    public long primitiveShort(short a, Long b, int c) {
        return a + b;
    }

    @Override
    public List<User> postList(List<User> users){
        return users;
    }
}
