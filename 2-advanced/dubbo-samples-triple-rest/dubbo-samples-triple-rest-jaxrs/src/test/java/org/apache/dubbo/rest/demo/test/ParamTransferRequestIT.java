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

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ParamTransferRequestIT extends BaseTest {

    @DubboReference(url = "tri://${dubbo.address:localhost}:50052")
    private ParamTransferRequestService paramTransferRequestService;

    @Test
    public void test() {
        String result1 = paramTransferRequestService.sayHello("world");
        Assertions.assertEquals("Hello world", result1);

        String result2 = paramTransferRequestService.sayForm("world");
        Assertions.assertEquals("Hello world", result2);

        String result3 = paramTransferRequestService.sayPath("1");
        Assertions.assertEquals("Hello 1", result3);

        String result4 = paramTransferRequestService.sayHeader("world");
        Assertions.assertEquals("Hello world", result4);

        String result5 = paramTransferRequestService.sayCookie("1");
        Assertions.assertEquals("Hello 1", result5);

        List<String> result6 = paramTransferRequestService.sayCookie(List.of("1", "2"));
        Assertions.assertEquals(List.of("1", "2"), result6);

        Map<String, String> result7 = paramTransferRequestService.sayCookie(Map.of("c1", "c", "c2", "d"));
        Assertions.assertEquals(Map.of("c1", "c", "c2", "d"), result7);

        String result8 = paramTransferRequestService.sayHeader(Map.of("name", "Hello"));
        Assertions.assertEquals("Hello", result8);

        String result9 = paramTransferRequestService.sayNoAnnoParam("world");
        Assertions.assertEquals("world", result9);

        String[] result10 = paramTransferRequestService.sayNoAnnoArrayParam(new String[] {"Hello", "world"});
        Assertions.assertArrayEquals(new String[] {"Hello", "world"}, result10);

        List<Long> result12 = paramTransferRequestService.sayList(List.of(1L, 2L, 3L));
        Assertions.assertEquals(List.of(1L, 2L, 3L), result12);

        List<String> result13 = paramTransferRequestService.sayNoAnnoListParam(List.of("Hello", "world"));
        Assertions.assertEquals(List.of("Hello", "world"), result13);

        Map<String, String> result14 = paramTransferRequestService.sayNoAnnoStringMapParam(Map.of("a", "world", "b", "hello"));
        Assertions.assertEquals(Map.of("a", "world", "b", "hello"), result14);

        String result15 = paramTransferRequestService.sayPath("1");
        Assertions.assertEquals("Hello 1", result15);

        List<String> result16 = paramTransferRequestService.sayQueryList(List.of("Hello ", "world"));
        Assertions.assertEquals(List.of("Hello ", "world"), result16);

        Map<String, String> result18 = paramTransferRequestService.sayQueryMap(Map.of("a", "world", "b", "hello"));
        Assertions.assertEquals(Map.of("a", "world", "b", "hello"), result18);

        Map<String, List<String>> result19 = paramTransferRequestService.sayQueryStringMap(Map.of("arg1", List.of("Hello", "world"), "arg2", List.of("!")));
        Assertions.assertEquals(Map.of("arg1", List.of("Hello", "world"), "arg2", List.of("!")), result19);

        Map<String, String> result20 = paramTransferRequestService.sayStringMap(Map.of("Hello", "world"));
        Assertions.assertEquals(Map.of("Hello", "world"), result20);

        User result21 = paramTransferRequestService.sayUser(new User(1L, "1", 1));
        Assertions.assertEquals(new User(1L, "1", 1), result21);
    }

    @Test
    public void testQuery() {
        ResponseEntity<String> response = restClient.get()
                .uri(toUri("/param/query?name={name}"), "world")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testQueryList() {
        ResponseEntity<List<String>> response = restClient.get()
                .uri(toUri("/param/queryList?name={name}&name={name}"), "Hello ", "world")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(List.of("Hello ", "world"), response.getBody());
    }

    @Test
    public void testQueryStringMap() {
        ResponseEntity<Map<String, List<String>>> response = restClient.get()
                .uri(toUri("/param/queryStringMap?arg1=Hello&arg1=world&arg2=!"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(Map.of("arg1", List.of("Hello", "world"), "arg2", List.of("!")), response.getBody());

    }

    @Test
    public void testQueryMap() {
        ResponseEntity<Map<String, String>> response = restClient.get()
                .uri(toUri("/param/queryMap?a=world&b=hello"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(Map.of("a", "world", "b", "hello"), response.getBody());
    }

    @Test
    public void testNoAnnoParam() {
        ResponseEntity<String> response = restClient.get()
                .uri(toUri("/param/noAnnoParam?name={name}"), "world")
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("world", response.getBody());
    }

    @Test
    public void testNoAnnoListParam() {
        ResponseEntity<List<String>> response = restClient.get()
                .uri(toUri("/param/noAnnoListParam?value={value}&value={value}"), "Hello ", "world")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(List.of("Hello ", "world"), response.getBody());
    }

    @Test
    public void testNoAnnoArrayParam() {
        ResponseEntity<String[]> response = restClient.get()
                .uri(toUri("/param/noAnnoArrayParam?value=Hello&value=world"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertArrayEquals(new String[] {"Hello", "world"}, response.getBody());

    }

    @Test
    public void testNoAnnoStringMap() {
        ResponseEntity<Map<String, String>> response = restClient.get()
                .uri(toUri("/param/noAnnoStringMapParam?a=world&b=hello"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(Map.of("a", "world", "b", "hello"), response.getBody());
    }

    @Test
    public void testPath() {
        ResponseEntity<String> response = restClient.get()
                .uri(toUri("/param/path/1"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello 1", response.getBody());
    }

    @Test
    public void testFrom() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("form", "world");
        ResponseEntity<String> response = restClient.post()
                .uri(toUri("/param/form"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());

    }

    @Test
    public void testHeader() {
        ResponseEntity<String> response = restClient.get()
                .uri(toUri("/param/header"))
                .accept(MediaType.TEXT_PLAIN)
                .header("name", "world")
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", response.getBody());
    }

    @Test
    public void testHeaderMap() {
        ResponseEntity<String> response = restClient.get()
                .uri(toUri("/param/header/map"))
                .header("name", "Hello")
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello", response.getBody());
    }

    @Test
    public void testCookie() {
        ResponseEntity<String> response = restClient.get()
                .uri(toUri("/param/cookie"))
                .accept(MediaType.TEXT_PLAIN)
                .header("Cookie", "cookieId=1")
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello 1", response.getBody());
    }

    @Test
    public void testCookieList() {
        ResponseEntity<List<String>> response = restClient.get()
                .uri(toUri("/param/cookie/list"))
                .header("Cookie", "cookieId=1")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(List.of("1"), response.getBody());
    }

    @Test
    public void testCookieMap() {
        ResponseEntity<Map<String, String>> response = restClient.get()
                .uri(toUri("/param/cookie/map"))
                .header("Cookie", "c1=c")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(Map.of("c1", "c"), response.getBody());
    }

    @Test
    public void testMatrix() {
        ResponseEntity<String> result = restClient.get()
                .uri(toUri("/param/matrix/string/m;name=world"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testMatrixList() {
        ResponseEntity<List<String>> result = restClient.get()
                .uri(toUri("/param/matrix/list/m;name=Hello;name=world"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(List.of("Hello", "world"), result.getBody());
    }

    @Test
    public void testMatrixMap() {
        ResponseEntity<String> result = restClient.get()
                .uri(toUri("/param/matrix/map/m;name=world"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("{\"name\":[\"world\"]}", result.getBody());
    }

    @Test
    public void testBodyUser() {
        ResponseEntity<User> response = restClient.post()
                .uri(toUri("/param/bodyUser"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new User(1L, "1", 1))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(new User(1L, "1", 1), response.getBody());
    }

    @Test
    public void testBodyList() {
        ResponseEntity<List<Long>> response = restClient.post()
                .uri(toUri("/param/bodyList"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(List.of(1L, 2L, 3L))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(List.of(1L, 2L, 3L), response.getBody());
    }

    @Test
    public void testBodyStringMap() {
        ResponseEntity<Map<String, String>> response = restClient.post()
                .uri(toUri("/param/bodyStringMap"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("Hello", "world"))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        Assertions.assertEquals(Map.of("Hello", "world"), response.getBody());
    }

    @Test
    public void testHttp() {
        ResponseEntity<String> result = restClient.get()
                .uri(toUri("/param/http"))
                .accept(MediaType.TEXT_PLAIN)
                .header("name", "Hello world")
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("Hello world", result.getBody());
    }

    @Test
    public void testHttpMethod() {
        ResponseEntity<String> result = restClient.get()
                .uri(toUri("/param/httpMethod"))
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("GET", result.getBody());
    }

    @Test
    public void testOutPut() {
        ResponseEntity<String> result = restClient.post()
                .uri(toUri("/param/output"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .toEntity(String.class);
        Assertions.assertEquals("world", result.getBody());
    }
}
