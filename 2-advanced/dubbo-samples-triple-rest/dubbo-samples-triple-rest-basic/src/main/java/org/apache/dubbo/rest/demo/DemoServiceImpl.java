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

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.remoting.http12.HttpResult;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@DubboService
public class DemoServiceImpl implements DemoService {

    private static final StringBuffer finalString = new StringBuffer();

    private static final Map<String, List<String>> finalMap = new LinkedHashMap<>();

    static {
        IntStream.range(0, 20000).forEach(i -> finalString.append(i).append("Hello"));
        finalMap.put("large-size-response-header", Collections.singletonList(finalString.toString()));
    }

    @Override
    public String hello(String name) {
        return finalString + "Hello " + name;
    }

    @Override
    public String hello(User user, int count) {
        return finalString + "Hello " + user.getTitle() + ". " + user.getName() + ", " + count;
    }

    @Override
    public String helloUser(User user) {
        return finalString + "Hello " + user.getTitle() + ". " + user.getName();
    }

    @Override
    public HttpResult<String> helloH2cWithExceededLargeSizeHeaderResp() {
        return new HttpResult<>() {

            @Override
            public int getStatus() {
                return 200;
            }

            @Override
            public Map<String, List<String>> getHeaders() {
                return finalMap;
            }

            @Override
            public String getBody() {
                return "Client should not get this because response header size exceeds limitation!";
            }
        };
    }
}
