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

package org.apache.dubbo.samples.cat.api.context;

import com.dianping.cat.Cat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CatContext implements Cat.Context, Serializable {

    private Map<String, String> properties = new HashMap<>();

    @Override
    public void addProperty(String s, String s1) {
        properties.put(s, s1);
    }

    @Override
    public String getProperty(String s) {
        return properties.get(s);
    }

}
