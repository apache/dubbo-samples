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
package org.apache.dubbo.rest.demo.test;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rest.demo.DemoService;
import org.apache.dubbo.rest.demo.expansion.filter.FilterService;
import org.apache.dubbo.rest.demo.pojo.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ConsumerIT extends BaseTest {

    private static final String PREFIX = "Hello ";

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private DemoService demoService;

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private FilterService filterService;

    @Test
    public void getParam() {
        String id = "123";
        String res = demoService.getParam(id);
        Assertions.assertEquals(PREFIX + id, res);
    }

    @Test
    public void RestGetParam() {
        String id = "123";
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(toUri("/demo/get/param?id=") + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + "\"", responseEntity.getBody());
    }

    @Test
    public void getVariable() {
        String id = "123";
        String res = demoService.getVariable(id);
        Assertions.assertEquals(PREFIX + id, res);
    }

    @Test
    public void RestGetVariable() {
        String id = "123";
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(toUri("/demo/get/variable/{id}"), id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + "\"", responseEntity.getBody());
    }

    @Test
    public void getMuchParam() {
        String id = "123", name = "test";
        String res = demoService.getMuchParam(id, name);
        Assertions.assertEquals(PREFIX + id + " " + name, res);
    }

    @Test
    public void RestGetMuchParam() {
        String id = "123", name = "test";
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(toUri("/demo/get/muchParam?id=") + id + "&name=" + name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + " " + name + "\"", responseEntity.getBody());
    }

    @Test
    public void getMuchVariable() {
        String id = "123", name = "test";
        String res = demoService.getMuchVariable(id, name);
        Assertions.assertEquals(PREFIX + id + " " + name, res);
    }

    @Test
    public void RestGetMuchVariable() {
        String id = "123", name = "test";
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(toUri("/demo/get/muchVariable/{id}/{name}"), id, name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + " " + name + "\"", responseEntity.getBody());
    }

    @Test
    public void getReg() {
        String name = "test", version = "2.2.1", ext = ".txt";
        String res = demoService.getReg(name, version, ext);
        Assertions.assertEquals(PREFIX + name + " " + version + " " + ext, res);
    }

    @Test
    public void RestGetReg() {
        String name = "test", version = "2.2.1", ext = ".txt";
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(toUri("/demo/get/reg/{name}-{version}{ext}"), name, version, ext)
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);

        // 因为已经解析了json 那么不需要再变成json格式了
        Assertions.assertEquals(PREFIX + name + " " + version + " " + ext, responseEntity.getBody());
    }

    @Test
    public void postBody() {
        String name = "123";
        String requestBody = "{\"name\": \"" + name + "\"}";
        String res = demoService.postBody(requestBody);
        Assertions.assertEquals(PREFIX + name, res);
    }

    @Test
    public void RestPostBody() {
        String name = "name";
        String requestBody = "{\"name\": \"" + name + "\"}";
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(toUri("/demo/post/body"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + name + "\"", responseEntity.getBody());
    }

    @Test
    public void postParam() {
        String name = "123";
        String res = demoService.postParam(name);
        Assertions.assertEquals(PREFIX + name, res);
    }

    @Test
    public void RestPostParam() {
        String id = "name";
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(toUri("/demo/post/param?id=") + id)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + "\"", responseEntity.getBody());
    }

    @Test
    public void postVariable() {
        String name = "123";
        String res = demoService.postVariable(name);
        Assertions.assertEquals(PREFIX + name, res);
    }

    @Test
    public void RestPostVariable() {
        String id = "name";
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(toUri("/demo/post/variable/{id}"), id)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + "\"", responseEntity.getBody());
    }

    @Test
    public void postUseConsumes() {
        String id = "123";
        String requestBody = "{\"id\": \"" + id + "\"}";
        String res = demoService.postUseConsumes(requestBody);
        Assertions.assertEquals(PREFIX + id, res);
    }

    @Test
    public void RestUseConsumes() {
        String id = "name";
        String requestBody = "{\"id\":\"" + id + "\"}";
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(toUri("/demo/post/useConsumes"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + "\"", responseEntity.getBody());
    }

    @Test
    public void postUseParams() {
        String id = "123";
        String requestBody = "{\"id\":\"" + id + "\"}";
        String res = demoService.postUseParams(requestBody);
        Assertions.assertEquals(PREFIX + id, res);
    }

    @Test
    public void RestUseParams() {
        String id = "name";
        String requestBody = "{\"id\":\"" + id + "\"}";
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(toUri("/demo/post/useParams?myParam=myValue"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + "\"", responseEntity.getBody());
    }

    @Test
    public void getHead() {
        String id = "123";
        String res = demoService.getHead(id);
        Assertions.assertEquals(PREFIX + id, res);
    }

    @Test
    public void RestGetHead() {
        String id = "123";
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(toUri("/demo/get/head/{id}"), id)
                .accept(MediaType.APPLICATION_JSON)
                .header("myHeader", "myValue")
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + "\"", responseEntity.getBody());
    }

    @Test
    public void RestPostUseConsumesFormData() {
        String id = "123", name = "John";

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id", id);
        map.add("name", name);

        ResponseEntity<String> responseEntity = restClient.post()
                .uri(toUri("/demo/post/useConsumes/formData"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(map)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + id + " " + name + "\"", responseEntity.getBody());
    }

    @Test
    public void postUser() {
        HashMap<String, User> map = new HashMap<>();
        map.put("user1", new User(123L, "nick"));
        map.put("user2", new User(456L, "lick"));

        String json = JSON.toJSONString(map);

        Assertions.assertEquals(PREFIX + "123" + " 456", demoService.postMapUser(json));
    }

    // 出现问题
    @Test
    public void RestPostUser() {
        HashMap<String, User> map = new HashMap<>();
        map.put("user1", new User(123L, "nick"));
        map.put("user2", new User(456L, "lick"));

        String json = JSON.toJSONString(map);

        ResponseEntity<String> responseEntity = restClient.post()
                .uri(toUri("/demo/post/map/user"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals("\"" + PREFIX + "123" + " 456" + "\"", responseEntity.getBody());
    }

    @Test
    public void putUpdateId() {
        String id = "123";
        Assertions.assertEquals(PREFIX + id, demoService.putUpdateId(id));
    }

    @Test
    public void RestPutUpdateId() {
        String id = "123";
        HttpEntity<String> response = restClient.put()
                .uri(toUri("/demo/put/update/{id}"), id)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("\"" + PREFIX + id + "\"", response.getBody());
    }

    @Test
    public void deleteId() {
        String id = "123";
        Assertions.assertEquals(PREFIX + id, demoService.deleteId(id));
    }

    @Test
    public void RestDeleteId() {
        String id = "123";

        HttpEntity<String> response = restClient.delete()
                .uri(toUri("/demo/delete/{id}"), id)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("\"" + PREFIX + id + "\"", response.getBody());
    }

    @Test
    public void patchById() {
        String id = "123";
        Assertions.assertEquals(PREFIX + id + " jack", demoService.patchById(id, new User(123L, "jack").stringToJson()));
    }

    @Test
    public void RestPatchById() {
        String id = "123";
        String requestBody = new User(12L, "jack").stringToJson();

        HttpEntity<String> response = restClient.patch()
                .uri(toUri("/demo/patch/{id}"), id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("\"" + PREFIX + id + " jack" + "\"", response.getBody());
    }

    @Test
    public void primitive() {
        Assertions.assertEquals(1 + 2, demoService.primitiveInt(1, 2));

        Assertions.assertEquals(1L + 2L, demoService.primitiveLong(1L, 2L));

        Assertions.assertEquals(1 + 2L, demoService.primitiveByte((byte) 1, 2L));

        Assertions.assertEquals(3L, demoService.primitiveShort((short) 1, 2L, 1));
    }

    @Test
    public void filterGet() {
        String name = "123";
        Assertions.assertEquals(name, filterService.filterGet(name));
    }

    @Test
    public void RestFilterGet() {
        String name = "123";
        HttpEntity<String> response = restClient.get()
                .uri(toUri("/filter/get/{name}"), name)
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);

        Assertions.assertEquals(name, response.getBody());
    }

    @Test
    public void postList() {
        List<User> userList = new LinkedList<>();
        userList.add(new User(123L, "jack"));
        userList.add(new User(345L, "mack"));

        Assertions.assertEquals(userList, demoService.postList(userList));
    }

    @Test
    public void RestPostList() {
        List<User> userList = new LinkedList<>();
        userList.add(new User(123L, "jack", 123));
        userList.add(new User(345L, "mack", 123));

        HttpEntity<List<User>> response = restClient.post()
                .uri(toUri("/demo/post/list"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(userList)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});

        Assertions.assertEquals(userList, response.getBody());
    }

}
