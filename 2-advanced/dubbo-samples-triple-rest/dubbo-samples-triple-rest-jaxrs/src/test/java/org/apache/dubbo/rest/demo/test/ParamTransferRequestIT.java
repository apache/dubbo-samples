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
import org.apache.dubbo.rest.demo.pojo.User;
import org.apache.dubbo.rest.demo.routine.ParamTransferRequestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ParamTransferRequestIT {

    private static final String providerAddress = System.getProperty("dubbo.address", "localhost");


    @DubboReference
    private ParamTransferRequestService paramTransferRequestService;

    @Test
    public void test() throws IOException {
        String result1 = paramTransferRequestService.sayHello("world");
        Assert.assertEquals("Hello world",result1);

        String result2 = paramTransferRequestService.sayForm("world");
        Assert.assertEquals("Hello world",result2);

        String result3 = paramTransferRequestService.sayPath("1");
        Assert.assertEquals("Hello 1",result3);

        String result4 = paramTransferRequestService.sayHeader("world");
        Assert.assertEquals("Hello world",result4);

        String result5 = paramTransferRequestService.sayCookie("1");
        Assert.assertEquals("Hello 1",result5);

        List<String> result6 = paramTransferRequestService.sayCookie(List.of("1","2"));
        Assert.assertEquals(List.of("1","2"),result6);

        Map<String, String> result7 = paramTransferRequestService.sayCookie(Map.of("c1","c","c2","d"));
        Assert.assertEquals(Map.of("c1","c","c2","d"),result7);

        String result8 = paramTransferRequestService.sayHeader(Map.of("name","Hello"));
        Assert.assertEquals("Hello",result8);

        String result9 = paramTransferRequestService.sayNoAnnoParam("world");
        Assert.assertEquals("world",result9);

        String[] result10 = paramTransferRequestService.sayNoAnnoArrayParam(new String[]{"Hello","world"});
        Assert.assertArrayEquals(new String[]{"Hello","world"},result10);

        List<Long> result12 = paramTransferRequestService.sayList(List.of(1L,2L,3L));
        Assert.assertEquals(List.of(1L,2L,3L),result12);

        List<String> result13 = paramTransferRequestService.sayNoAnnoListParam(List.of("Hello","world"));
        Assert.assertEquals(List.of("Hello","world"),result13);

        Map<String, String> result14 = paramTransferRequestService.sayNoAnnoStringMapParam(Map.of("a","world","b","hello"));
        Assert.assertEquals(Map.of("a","world","b","hello"),result14);

        String result15 = paramTransferRequestService.sayPath("1");
        Assert.assertEquals("Hello 1",result15);

        List<String> result16 = paramTransferRequestService.sayQueryList(List.of("Hello ","world"));
        Assert.assertEquals(List.of("Hello ","world"),result16);

        Map<String, String> result18 = paramTransferRequestService.sayQueryMap(Map.of("a","world","b","hello"));
        Assert.assertEquals(Map.of("a","world","b","hello"),result18);

        Map<String, List<String>> result19 = paramTransferRequestService.sayQueryStringMap(Map.of("arg1",List.of("Hello","world"),"arg2",List.of("!")));
        Assert.assertEquals(Map.of("arg1",List.of("Hello","world"),"arg2",List.of("!")),result19);

        Map<String, String> result20 = paramTransferRequestService.sayStringMap(Map.of("Hello","world"));
        Assert.assertEquals(Map.of("Hello","world"),result20);

        User result21 = paramTransferRequestService.sayUser(new User(1L, "1", 1));
        Assert.assertEquals(new User(1L,"1",1),result21);
    }


    @Test
    public void testQuery(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/query?name={name}","world")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }

    @Test
    public void testQueryList(){
        ResponseEntity<List<String>> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/queryList?name={name}&name={name}","Hello ","world")
                .header( "Content-type","application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<String>>() {});
        Assert.assertEquals(List.of("Hello ","world"),response.getBody());
    }

    @Test
    public void testQueryStringMap(){
        ResponseEntity<Map<String,List<String>>> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/queryStringMap?arg1=Hello&arg1=world&arg2=!")
                .header( "Content-type","application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String,List<String>>>() {});
        Assert.assertEquals(Map.of("arg1",List.of("Hello","world"),"arg2",List.of("!")),response.getBody());

    }

    @Test
    public void testQueryMap(){
        ResponseEntity<Map<String,String>> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/queryMap?a=world&b=hello")
                .header( "Content-type","application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, String>>() {
                });
        Assert.assertEquals(Map.of("a","world","b","hello"),response.getBody());
    }

    @Test
    public void testNoAnnoParam(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/noAnnoParam?name={name}","world")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("world",response.getBody());
    }

    @Test
    public void testNoAnnoListParam(){
        ResponseEntity<List<String>> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/noAnnoListParam?value={value}&value={value}","Hello ","world")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<String>>() {});
        Assert.assertEquals(List.of("Hello ","world"),response.getBody());
    }

    @Test
    public void testNoAnnoArrayParam(){
        ResponseEntity<String[]> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/noAnnoArrayParam?value=Hello&value=world")
                .header( "Content-type","application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<String[]>() {
                });
        Assert.assertArrayEquals(new String[]{"Hello","world"}, response.getBody());

    }

    @Test
    public void testNoAnnoStringMap(){
        ResponseEntity<Map<String,String>> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/noAnnoStringMapParam?a=world&b=hello")
                .header( "Content-type","application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, String>>() {
                });
        Assert.assertEquals(Map.of("a","world","b","hello"),response.getBody());
    }


    @Test
    public void testPath(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/path/1")
                .header( "Content-type","text/plain")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello 1",response.getBody());
    }

    @Test
    public void testFrom(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("form","world");
        ResponseEntity<String> response = RestClient.create().post()
                .uri("http://" + providerAddress + ":50052/param/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world", response.getBody());

    }

    @Test
    public void testHeader(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/header")
                .header( "Content-type","text/plain")
                .header("name","world")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world",response.getBody());
    }

    @Test
    public void testHeaderMap(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/header/map")
                .header("name","Hello")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello",response.getBody());
    }

    @Test
    public void testCookie(){
        ResponseEntity<String> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/cookie")
                .header( "Content-type","text/plain")
                .header( "Cookie","cookieId=1")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello 1",response.getBody());
    }

    @Test
    public void testCookieList(){
        ResponseEntity<List<String>> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/cookie/list")
                .header( "Cookie","cookieId=1")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<String>>() {
                });
        Assert.assertEquals(List.of("1"),response.getBody());
    }

    @Test
    public void testCookieMap(){
        ResponseEntity<Map<String,String>> response = RestClient.create().get()
                .uri("http://" + providerAddress + ":50052/param/cookie/map")
                .header( "Cookie","c1=c")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, String>>() {
                });
        Assert.assertEquals(Map.of("c1","c"),response.getBody());
    }

    @Test
    public void testMatrix(){
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/param/matrix;m=name=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testMatrixList(){
        RestClient defaultClient = RestClient.create();
        ResponseEntity<List<String>> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/param/matrix/list;m=list=Hello;list=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<String>>() {
                });
        Assert.assertEquals(List.of("Hello","world"), result.getBody());
    }

    @Test
    public void testMatrixMap(){
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/param/matrix;m=name=world")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testBodyUser(){
        ResponseEntity<User> response = RestClient.create().post()
                .uri("http://" + providerAddress + ":50052/param/bodyUser" )
                .header( "Content-type","application/json")
                .body(new User(1L,"1",1))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<User>() {});
        Assert.assertEquals(new User(1L,"1",1),response.getBody());
    }

    @Test
    public void testBodyList(){
        ResponseEntity<List<Long>> response = RestClient.create().post()
                .uri("http://" + providerAddress + ":50052/param/bodyList" )
                .header( "Content-type","application/json")
                .body(List.of(1L,2L,3L))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Long>>() {});
        Assert.assertEquals(List.of(1L,2L,3L),response.getBody());
    }
    @Test
    public void testBodyStringMap(){
        ResponseEntity<Map<String,String>> response = RestClient.create().post()
                .uri("http://" + providerAddress + ":50052/param/bodyStringMap" )
                .header( "Content-type","application/json")
                .body(Map.of("Hello","world"))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String,String>>() {});
        Assert.assertEquals(Map.of("Hello","world"),response.getBody());
    }

    @Test
    public void testHttp(){
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/param/http")
                .header("Content-type", "application/json")
                .header("name","Hello world")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testHttpMethod(){
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.get()
                .uri("http://" + providerAddress + ":50052/param/httpMethod")
                .header("Content-type", "application/json")
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("GET", result.getBody());
    }

    @Test
    public void testOutPut() throws IOException {
        RestClient defaultClient = RestClient.create();
        ResponseEntity<String> result = defaultClient.post()
                .uri("http://" + providerAddress + ":50052/param/output")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .toEntity(String.class);
        Assert.assertEquals("world", result.getBody());
    }
}
