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
package org.apache.dubbo.samples.tengine;

import java.util.Map;

public interface DemoService {;

    /**
     * standard samples tengine dubbo infterace demo
     * @param context tengine pass http infos
     * @return Map<String, Object></> pass to tengine response http
     **/
    Map<String, Object> tengineDubbo(Map<String, Object> context);

    /**
     * a test sample for dubbo to http invoke
     * @param context tengine pass http infos
     * @return Map<String, Object></> pass to tengine response http
     **/
    Map<String, Object> dubbo2Http(Map<String, Object> context);

    /**
     * a test for dubbo boundary case
     * @param context tengine pass http infos
     * @return Map<String, Object></> pass to tengine response http
     **/
    Map<String, Object> tengineTest(Map<String, Object> context);
}