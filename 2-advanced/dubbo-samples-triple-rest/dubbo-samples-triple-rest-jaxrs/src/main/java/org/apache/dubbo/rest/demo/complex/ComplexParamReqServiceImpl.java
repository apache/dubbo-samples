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

package org.apache.dubbo.rest.demo.complex;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rest.demo.pojo.Person;
import org.apache.dubbo.rest.demo.pojo.User;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DubboService
public class ComplexParamReqServiceImpl implements ComplexParamRequestService {

    @Override
    public List<User> list(List<User> users) {
        return users;
    }

    @Override
    public Set<User> set(Set<User> users) {
        return users;
    }

    @Override
    public User[] array(User[] users) {
        return users;
    }

    @Override
    public Map<String, User> stringMap(Map<String, User> userMap) {
        return userMap;
    }

    @Override
    public List<String> testMapParam(Map<String, String> map) {
        return map.values().stream().toList();
    }

    @Override
    public String testMapHeader(String headers) {
        return headers;
    }

    @Override
    public List<String> testMapForm(MultivaluedMap<String, String> params) {
        return params.values().stream().flatMap(List::stream).toList();
    }

    @Override
    public String testHeader(HttpHeaders headers) {
        return headers.getHeaderString("name");
    }

    @Override
    public String testUriInfo(UriInfo uriInfo) {
        return uriInfo.getPath();
    }

    @Override
    public String testForm(Person person) {
        return person.getName();
    }

    @Override
    public Person testXml(Person person) {
        return person;
    }

    @Override
    public String testCookie(Cookie cookie) {
        return cookie.getValue();
    }

}
