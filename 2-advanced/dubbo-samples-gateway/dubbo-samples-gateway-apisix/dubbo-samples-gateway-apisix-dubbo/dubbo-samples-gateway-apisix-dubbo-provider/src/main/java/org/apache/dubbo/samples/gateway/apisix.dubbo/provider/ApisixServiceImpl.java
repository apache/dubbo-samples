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
package org.apache.dubbo.samples.gateway.apisix.dubbo.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.samples.gateway.apisix.dubbo.api.ApisixService;

import java.util.HashMap;
import java.util.Map;

@DubboService
public class ApisixServiceImpl implements ApisixService {

    @Override
    public Map<String, Object> apisixToDubbo(Map<String, Object> requestBody) {
        for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
            //Notice here that the value of the body is actually a byte array
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("body", "dubbo success\n"); // http response body
        resp.put("status", "200"); // http response status
        resp.put("author", "yang siming"); // http response header

        return resp;
    }

}
