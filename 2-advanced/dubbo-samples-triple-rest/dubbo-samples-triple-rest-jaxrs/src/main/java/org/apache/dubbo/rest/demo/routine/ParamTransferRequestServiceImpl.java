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

package org.apache.dubbo.rest.demo.routine;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.remoting.http12.HttpMethods;
import org.apache.dubbo.remoting.http12.HttpRequest;
import org.apache.dubbo.remoting.http12.HttpResponse;
import org.apache.dubbo.rest.demo.pojo.User;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@DubboService
public class ParamTransferRequestServiceImpl implements ParamTransferRequestService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public List<String> sayQueryList(List<String> values) {
        return values;
    }

    @Override
    public Map<String, String> sayQueryMap(Map<String, String> value) {
        return value;
    }

    @Override
    public Map<String, List<String>> sayQueryStringMap(Map<String, List<String>> value) {
        return value;
    }

    @Override
    public String sayNoAnnoParam(String name) {
        return name;
    }

    @Override
    public List<String> sayNoAnnoListParam(List<String> value) {
        return value;
    }

    @Override
    public Map<String, String> sayNoAnnoStringMapParam(Map<String, String> value) {
        return value;
    }

    @Override
    public String[] sayNoAnnoArrayParam(String[] value) {
        return value;
    }

    @Override
    public String sayForm(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayPath(String id) {
        return "Hello " + id;
    }

    @Override
    public String sayHeader(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHeader(Map<String, String> value) {
        return value.get("name");
    }

    @Override
    public String sayCookie(String cookieId) {
        return "Hello " + cookieId;
    }

    @Override
    public List<String> sayCookie(List<String> values) {
        return values;
    }

    @Override
    public Map<String, String> sayCookie(Map<String, String> value) {
        return value;
    }

    @Override
    public String sayMatrixString(String m, String name) {
        return "Hello " + name;
    }

    @Override
    public List<String> sayMatrixList(String m, List<String> values) {
        return values;
    }

    @Override
    public Map<String, List<String>> sayMatrixMap(String m, Map<String, List<String>> valueMap) {
        return valueMap;
    }

    @Override
    public User sayUser(User users) {
        return users;
    }

    @Override
    public List<Long> sayList(List<Long> list) {
        return list;
    }

    @Override
    public Map<String, String> sayStringMap(Map<String, String> value) {
        return value;
    }

    @Override
    public String sayOutput(OutputStream out) throws IOException {
        out.write("world".getBytes());
        return out.toString();
    }

    @Override
    public String sayHttpMethod(HttpMethods methods) {
        return methods.name();
    }

    @Override
    public void sayHttpRequest(HttpRequest request, HttpResponse response) {
        String name = request.header("name");
        response.setBody(name);
    }

}
