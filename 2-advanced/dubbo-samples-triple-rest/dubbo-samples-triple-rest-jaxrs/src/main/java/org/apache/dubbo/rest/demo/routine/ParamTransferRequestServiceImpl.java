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
import org.apache.dubbo.rest.demo.pojo.Person;

@DubboService
public class ParamTransferRequestServiceImpl implements ParamTransferRequestService{
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
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
    public String sayCookie(String cookieId) {
        return "Hello " + cookieId;
    }

    @Override
    public String sayMatrix(String name) {
        return "Hello " + name;
    }


}
