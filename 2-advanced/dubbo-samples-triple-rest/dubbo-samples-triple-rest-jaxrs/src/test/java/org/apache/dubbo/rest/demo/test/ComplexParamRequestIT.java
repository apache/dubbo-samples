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
import org.apache.dubbo.rest.demo.complex.ComplexParamRequestService;
import org.apache.dubbo.rest.demo.pojo.Person;
import org.apache.dubbo.rest.demo.pojo.User;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

public class ComplexParamRequestIT extends BaseTest {

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private ComplexParamRequestService complexParamRequestService;

    @Test
    public void test() {
        List<User> list = List.of(new User(1L, "1", 1), new User(2L, "2", 2));
        List<User> result1 = complexParamRequestService.list(list);
        Assertions.assertEquals(list, result1);

        Set<User> set = Set.of(new User(1L, "1", 1), new User(2L, "2", 2));
        Set<User> result2 = complexParamRequestService.set(set);
        Assertions.assertEquals(set, result2);

        User[] arr = {new User(1L, "1", 1), new User(2L, "2", 2)};
        User[] result3 = complexParamRequestService.array(arr);
        Assertions.assertArrayEquals(arr, result3);

        Map<String, User> map = Map.of("user1", new User(1L, "1", 1), "user2", new User(2L, "2", 2));
        Map<String, User> result4 = complexParamRequestService.stringMap(map);
        Assertions.assertEquals(map, result4);

        MultivaluedHashMap<String, String> valueMap = new MultivaluedHashMap<>();
        valueMap.add("arg1", "Hello");
        valueMap.add("arg2", "world");
        List<String> result5 = complexParamRequestService.testMapForm(valueMap);
        Assertions.assertEquals(valueMap.values().stream().flatMap(List::stream).toList(), result5);

        String result6 = complexParamRequestService.testMapHeader("Head");
        Assertions.assertEquals("Head", result6);

        Map<String, String> stringMap = Map.of("Hello", "World");
        List<String> result7 = complexParamRequestService.testMapParam(stringMap);
        Assertions.assertEquals(stringMap.values().stream().toList(), result7);

        Person person = complexParamRequestService.testXml(new Person("1"));
        Assertions.assertEquals(new Person("1"), person);

    }

    @Test
    public void testList() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User(1L, "1", 1));
        list.add(new User(2L, "2", 2));
        ResponseEntity<List<User>> response = RestClient.create()
                .post()
                .uri(toUri("/complex/list"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(list)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(list, response.getBody());
    }

    @Test
    public void testSet() {
        Set<User> set = new HashSet<>();
        set.add(new User(1L, "1", 1));
        set.add(new User(2L, "2", 2));
        ResponseEntity<Set<User>> response = RestClient.create()
                .post()
                .uri(toUri("/complex/set"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(set)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(set, response.getBody());
    }

    @Test
    public void testArray() {
        User[] array = {new User(1L, "1", 1), new User(2L, "2", 2)};
        ResponseEntity<User[]> response = RestClient.create()
                .post()
                .uri(toUri("/complex/array"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(array)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertArrayEquals(array, response.getBody());
    }

    @Test
    public void testStringMap() {
        HashMap<String, User> map = new HashMap<>();
        map.put("user1", new User(1L, "1", 1));
        map.put("user2", new User(2L, "2", 2));
        ResponseEntity<Map<String, User>> response = RestClient.create()
                .post()
                .uri(toUri("/complex/stringMap"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(map)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(map, response.getBody());
    }

    @Test
    public void testHeader() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/complex/testMapHeader"))
                .accept(MediaType.TEXT_PLAIN)
                .header("headers", "Head")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals("Head", response.getBody());
    }

    @Test
    public void testMapParam() {
        ResponseEntity<List<String>> response = RestClient.create()
                .get()
                .uri(toUri("/complex/testMapParam?arg1=World&arg2=Hello"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(List.of("Hello", "World"), response.getBody());
    }

    @Test
    public void testMapForm() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("arg1", "Hello");
        ResponseEntity<List<String>> response = RestClient.create()
                .post()
                .uri(toUri("/complex/testMapForm"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(List.of("Hello"), response.getBody());
    }

    @Test
    public void testXml() throws JAXBException {
        Person person = new Person("Sam");

        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
        StringWriter writer = new StringWriter();
        jaxbContext.createMarshaller().marshal(person, writer);

        String result = restClient.post()
                .uri(toUri("/complex/xml"))
                .contentType(MediaType.APPLICATION_XML)
                .body(writer.toString())
                .retrieve()
                .body(String.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(person, jaxbContext.createUnmarshaller().unmarshal(new StringReader(result)));
    }

    @Test
    public void testCookie() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/complex/cookie"))
                .accept(MediaType.TEXT_PLAIN)
                .header("cookie", "cookie=1")
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("1", response.getBody());
    }

    @Test
    public void testHttpHeader() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/complex/httpHeader"))
                .accept(MediaType.TEXT_PLAIN)
                .header("name", "world")
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("world", response.getBody());
    }

    @Test
    public void testUri() {
        ResponseEntity<String> response = RestClient.create()
                .get()
                .uri(toUri("/complex/uri"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("/complex/uri", response.getBody());
    }

    @Test
    public void testAnnoFrom() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "Li");
        ResponseEntity<String> response = RestClient.create()
                .post()
                .uri(toUri("/complex/annoForm"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Li", response.getBody());
    }

}
