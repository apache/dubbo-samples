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

import com.alibaba.fastjson2.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rest.demo.DemoService;
import org.apache.dubbo.rest.demo.expansion.filter.FilterService;
import org.apache.dubbo.rest.demo.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerIT {
    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");
    private static final String urlPrefix = "http://" + providerAddress + ":50052";
    private static final String prefix = "hello ";

    @DubboReference
    private DemoService demoService;

    @DubboReference
    private FilterService filterService;

    @Test
    public void getParam(){
        String id = "123";
        String res = demoService.getParam(id);
        Assert.assertEquals(prefix + id, res);
    }

    @Test
    public void RestGetParam(){
        String id = "123";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(urlPrefix + "/demo/get/param?id=" + id)
                .header("Content-Type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + "\"", responseEntity.getBody());
    }

    @Test
    public void getVariable(){
        String id = "123";
        String res = demoService.getVariable(id);
        Assert.assertEquals(prefix + id, res);
    }

    @Test
    public void RestGetVariable(){
        String id = "123";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(urlPrefix + "/demo/get/variable/{id}", id)
                .header("Content-Type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + "\"", responseEntity.getBody());
    }

    @Test
    public void getMuchParam(){
        String id = "123", name = "test";
        String res = demoService.getMuchParam(id, name);
        Assert.assertEquals(prefix + id + " " + name, res);
    }

    @Test
    public void RestGetMuchParam(){
        String id = "123", name = "test";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(urlPrefix + "/demo/get/muchParam?id=" + id + "&name=" + name)
                .header("Content-Type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + " " + name + "\"", responseEntity.getBody());
    }

    @Test
    public void getMuchVariable(){
        String id = "123", name = "test";
        String res = demoService.getMuchVariable(id, name);
        Assert.assertEquals(prefix + id + " " + name, res);
    }

    @Test
    public void RestGetMuchVariable(){
        String id = "123", name = "test";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(urlPrefix + "/demo/get/muchVariable/{id}/{name}", id, name)
                .header("Content-Type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + " " + name + "\"", responseEntity.getBody());
    }

    @Test
    public void getReg(){
        String name = "test", version = "2.2.1", ext = ".txt";
        String res = demoService.getReg(name, version, ext);
        Assert.assertEquals(prefix + name + " " +  version + " " + ext, res);
    }

    @Test
    public void RestGetReg(){
        String name = "test", version = "2.2.1", ext = ".txt";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(urlPrefix + "/demo/get/reg/{name}-{version}{ext}", name, version, ext)
                .header("Content-Type", "application/json")
                .retrieve()
                .toEntity(String.class);

        // 因为已经解析了json 那么不需要再变成json格式了
        Assert.assertEquals(prefix + name + " " + version + " " + ext, responseEntity.getBody());
    }

    @Test
    public void postBody(){
        String name = "123";
        String requestBody = "{\"name\": \"" + name + "\"}";
        String res = demoService.postBody(requestBody);
        Assert.assertEquals(prefix + name, res);
    }

    @Test
    public void RestPostBody(){
        String name = "name";
        String requestBody = "{\"name\": \"" + name + "\"}";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(urlPrefix + "/demo/post/body")
                .header("content-type", "application/json")
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + name + "\"", responseEntity.getBody());
    }

    @Test
    public void postParam(){
        String name = "123";
        String res = demoService.postParam(name);
        Assert.assertEquals(prefix + name, res);
    }

    @Test
    public void RestPostParam(){
        String id = "name";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(urlPrefix + "/demo/post/param?id=" + id)
                .contentType(APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + "\"", responseEntity.getBody());
    }

    @Test
    public void postVariable(){
        String name = "123";
        String res = demoService.postVariable(name);
        Assert.assertEquals(prefix + name, res);
    }

    @Test
    public void RestPostVariable(){
        String id = "name";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(urlPrefix + "/demo/post/variable/{id}", id)
                .contentType(APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + "\"", responseEntity.getBody());
    }

    @Test
    public void postUseConsumes(){
        String id = "123";
        String requestBody = "{\"id\": \"" + id + "\"}";
        String res = demoService.postUseConsumes(requestBody);
        Assert.assertEquals(prefix + id, res);
    }

    @Test
    public void RestUseConsumes(){
        String id = "name";
        String requestBody = "{\"id\":\"" + id + "\"}";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(urlPrefix + "/demo/post/useConsumes")
                .header("content-type", "application/json")
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + "\"", responseEntity.getBody());
    }

    @Test
    public void postUseParams(){
        String id = "123";
        String requestBody = "{\"id\":\"" + id + "\"}";
        String res = demoService.postUseParams(requestBody);
        Assert.assertEquals(prefix + id, res);
    }

    @Test
    public void RestUseParams(){
        String id = "name";
        String requestBody = "{\"id\":\"" + id + "\"}";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(urlPrefix + "/demo/post/useParams?myParam=myValue")
                .contentType(APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + "\"", responseEntity.getBody());
    }

    @Test
    public void getHead(){
        String id = "123";
        String res = demoService.getHead(id);
        Assert.assertEquals(prefix + id, res);
    }

    @Test
    public void RestGetHead(){
        String id = "123";
        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.get()
                .uri(urlPrefix + "/demo/get/head/{id}", id)
                .header("Content-Type", "application/json")
                .header("myHeader", "myValue")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + "\"", responseEntity.getBody());
    }


    @Test
    public void RestPostUseConsumesFormData(){
        String id = "123", name = "John";

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id", id);
        map.add("name", name);

        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(urlPrefix + "/demo/post/useConsumes/formData")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(map)
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + id + " " + name + "\"", responseEntity.getBody());
    }

    @Test
    public void postUser(){
        HashMap<String, User> map = new HashMap<String, User>();
        map.put("user1", new User(123L, "nick"));
        map.put("user2", new User(456L, "lick"));

        String json = JSON.toJSONString(map);

        Assert.assertEquals(prefix + "123" + " 456", demoService.postMapUser(json));
    }

    // 出现问题
    @Test
    public void RestPostUser(){
        HashMap<String, User> map = new HashMap<String, User>();
        map.put("user1", new User(123L, "nick"));
        map.put("user2", new User(456L, "lick"));

        String json = JSON.toJSONString(map);

        RestClient restClient = RestClient.create();
        ResponseEntity<String> responseEntity = restClient.post()
                .uri(urlPrefix + "/demo/post/map/user")
                .header("content-type", "application/json")
                .body(json)
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals("\"" + prefix + "123" + " 456" + "\"", responseEntity.getBody());
    }

    @Test
    public void putUpdateId(){
        String id = "123";
        Assert.assertEquals(prefix + id, demoService.putUpdateId(id));
    }


    @Test
    public void RestPutUpdateId(){
        String id = "123";
        RestClient restClient = RestClient.create();
        HttpEntity<String> response = restClient.put()
                .uri(urlPrefix + "/demo/put/update/{id}", id)
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("\"" + prefix + id + "\"", response.getBody());
    }

    @Test
    public void deleteId(){
        String id = "123";
        Assert.assertEquals(prefix + id, demoService.deleteId(id));
    }


    @Test
    public void RestDeleteId(){
        String id = "123";

        RestClient restClient = RestClient.create();
        HttpEntity<String> response = restClient.delete()
                .uri(urlPrefix + "/demo/delete/{id}", id)
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("\"" + prefix + id + "\"", response.getBody());
    }

    @Test
    public void patchById(){
        String id = "123";
        Assert.assertEquals(prefix + id + " jack", demoService.patchById(id, new User(123L, "jack").stringToJson()));
    }


    @Test
    public void RestPatchById(){
        String id = "123";
        String requestBody = new User(12L, "jack").stringToJson();

        RestClient restClient = RestClient.create();
        HttpEntity<String> response = restClient.patch()
                .uri(urlPrefix + "/demo/patch/{id}", id)
                .contentType(APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("\"" + prefix + id + " jack" +"\"", response.getBody());
    }

    @Test
    public void primitive(){
        Assert.assertEquals(1 + 2, demoService.primitiveInt(1, 2));

        Assert.assertEquals(1L + 2L, demoService.primitiveLong(1L, 2L));

        Assert.assertEquals(1 + 2L, demoService.primitiveByte((byte) 1, 2L));

        Assert.assertEquals(3L, demoService.primitiveShort((short) 1, 2L, 1));
    }

    @Test
    public void filterGet(){
        String name = "123";
        Assert.assertEquals(name, filterService.filterGet(name));
    }

    @Test
    public void RestFilterGet(){
        String name = "123";
        RestClient restClient = RestClient.create();
        HttpEntity<String> response = restClient.get()
                .uri(urlPrefix + "/filter/get/{name}", name)
                .header("content-type", "application/json")
                .retrieve()
                .toEntity(String.class);

        Assert.assertEquals(name, response.getBody());
    }

    @Test
    public void postList(){
        List<User> userList = new LinkedList<>();
        userList.add(new User(123L, "jack"));
        userList.add(new User(345L, "mack"));

        Assert.assertEquals(userList, demoService.postList(userList));
    }

    @Test
    public void RestPostList(){
        List<User> userList = new LinkedList<>();
        userList.add(new User(123L, "jack", 123));
        userList.add(new User(345L, "mack", 123));

        RestClient restClient = RestClient.create();
        HttpEntity<List<User>> response = restClient.post()
                .uri(urlPrefix + "/demo/post/list")
                .contentType(APPLICATION_JSON)
                .body(userList)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<User>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });

        Assert.assertEquals(userList, response.getBody());
    }

}
