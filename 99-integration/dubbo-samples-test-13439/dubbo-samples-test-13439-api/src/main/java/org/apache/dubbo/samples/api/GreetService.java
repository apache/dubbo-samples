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

package org.apache.dubbo.samples.api;

public interface GreetService {

    String greet(String msg);

    String HOST = System.getProperty("nacos.address") + ":" + System.getProperty("nacos.port");

    String NACOS_ADDR = "nacos://"+HOST;

    String NACOS_NAMESPACE_CONSOLE_PATH = "http://"+HOST+"/nacos/v1/console/namespaces";

    String NACOS_NAMESPACE_NAME_1 = "DUBBO-TEST-1";

    String NACOS_NAMESPACE_NAME_2 = "DUBBO-TEST-2";

    String NACOS_NAMESPACE_1_PATH = NACOS_ADDR + "?namespace="+NACOS_NAMESPACE_NAME_1;

    String NACOS_NAMESPACE_2_PATH = NACOS_ADDR + "?namespace="+NACOS_NAMESPACE_NAME_2;

    int PORT = 10001;
}
