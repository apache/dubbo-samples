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
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ComplexParamRequestIT {
    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");

    @DubboReference
    private ComplexParamRequestService complexParamRequestService;

    @Test
    public void test(){
        List<User> list = List.of(new User(1L, "1", 1), new User(2L, "2", 2));
        List<User> result1 = complexParamRequestService.list(list);
        Assert.assertEquals(list,result1);

        Set<User> set = Set.of(new User(1L, "1", 1), new User(2L, "2", 2));
        Set<User> result2 = complexParamRequestService.set(set);
        Assert.assertEquals(set,result2);

        User[] arr ={new User(1L, "1", 1), new User(2L, "2", 2)};
        User[] result3 = complexParamRequestService.array(arr);
        Assert.assertArrayEquals(arr,result3);

        Map<String, User> map = Map.of("user1", new User(1L, "1", 1), "user2", new User(2L, "2", 2));
        Map<String, User> result4 = complexParamRequestService.stringMap(map);
        Assert.assertEquals(map,result4);

        MultivaluedHashMap<String,String> valueMap = new MultivaluedHashMap<>();
        valueMap.add("arg1","Hello");
        valueMap.add("arg2","world");
        List<String> result5 = complexParamRequestService.testMapForm(valueMap);
        Assert.assertEquals(valueMap.values().stream().flatMap(List::stream).toList(),result5);

        String result6 = complexParamRequestService.testMapHeader("Head");
        Assert.assertEquals("Head",result6);

        Map<String, String> stringMap = Map.of("Hello", "World");
        List<String> result7 = complexParamRequestService.testMapParam(stringMap);
        Assert.assertEquals(stringMap.values().stream().toList(),result7);

        Person person = complexParamRequestService.testXml(new Person("1"));
        Assert.assertEquals(new Person("1"),person);

    }


    @Test
    public void testList() throws Exception {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User(1L,"1",1));
        list.add(new User(2L,"2",2));
        ResponseEntity<List<User>> response = RestClient.create().post()
                .uri("http://" + providerAddress +":50052/complex/list")
                .contentType(APPLICATION_JSON)
                .body(list)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<User>>() {
                });
        Assert.assertEquals(list,response.getBody());
    }

    @Test
    public void testSet() throws Exception {
        Set<User> set = new HashSet<>();
        set.add(new User(1L,"1",1));
        set.add(new User(2L,"2",2));
        ResponseEntity<Set<User>> response = RestClient.create().post()
                .uri("http://" + providerAddress +":50052/complex/set")
                .contentType(APPLICATION_JSON)
                .body(set)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Set<User>>() {
                });
        Assert.assertEquals(set,response.getBody());
    }

    @Test
    public void testArray() throws Exception {
        User[] array = {new User(1L,"1",1),new User(2L,"2",2)};
        ResponseEntity<User[]> response = RestClient.create().post()
                .uri("http://" + providerAddress +":50052/complex/array")
                .contentType(APPLICATION_JSON)
                .body(array)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<User[]>() {
                });
        Assert.assertArrayEquals(array, response.getBody());
    }


    @Test
    public void testStringMap() throws Exception {
        HashMap<String, User> map = new HashMap<>();
        map.put("user1",new User(1L,"1",1));
        map.put("user2",new User(2L,"2",2));
        ResponseEntity<Map<String,User>> response = RestClient.create().post()
                .uri("http://" + providerAddress +":50052/complex/stringMap")
                .contentType(APPLICATION_JSON)
                .body(map)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String,User>>() {
                });
        Assert.assertEquals(map,response.getBody());
    }

    @Test
    public void testHeader() throws Exception {
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress +":50052/complex/testMapHeader")
                .header("Content-type", "application/json")
                .header("headers","Head")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<String>() {
                });
        Assert.assertEquals("Head",response.getBody());
    }


    @Test
    public void testMapParam() throws Exception {
        ResponseEntity<List<String>> response = RestClient.create().get()
                .uri("http://" + providerAddress +":50052/complex/testMapParam?arg1=World&arg2=Hello")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<String>>() {
                });
        Assert.assertEquals(List.of("Hello","World"),response.getBody());
    }


    @Test
    public void testMapForm() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("arg1","Hello");
        ResponseEntity<List<String>> response = RestClient.create().post()
                .uri("http://" + providerAddress +":50052/complex/testMapForm")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<String>>() {
                });
        Assert.assertEquals(List.of("Hello"),response.getBody());
    }

    @Test
    public void testXml() throws Exception {
        String str = "<?xml  version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><person><name>1</name></person>";
        Person person = new Person("1");

        RestClient defaultClient = RestClient.create();
        Person result = defaultClient.post()
                .uri("http://" + providerAddress + ":50052/complex/xml")
                .header("Content-type", "text/xml")
                .accept(MediaType.APPLICATION_XML)
                .body(str)
                .exchange((request,response)->{
                    if(response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
                        try {
                            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
                            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                            return  (Person) jaxbUnmarshaller.unmarshal(response.getBody());
                        } catch (JAXBException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        throw new RuntimeException("http code erroe");
                    }
                });
        Assert.assertEquals(person,result);
    }

    @Test
    public void testCookie(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/complex/cookie")
                .header("Content-type", "application/json")
                .header( "cookie","cookie=1")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("1",response.getBody());
    }

    @Test
    public void testHttpHeader(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/complex/httpHeader")
                .header( "Content-type","text/plain")
                .header( "name","world")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("world", response.getBody());
    }

    @Test
    public void testUri(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/complex/uri")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("/complex/uri", response.getBody());
    }

    @Test
    public void testAnnoFrom(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name","Li");
        ResponseEntity<String> response = RestClient.create().post()
                .uri("http://" + providerAddress + ":50052/complex/annoForm")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Li", response.getBody());
    }

}
